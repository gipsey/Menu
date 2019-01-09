package org.davidd.menu.common

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.content.Context
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.*
import dagger.multibindings.IntoMap
import org.davidd.menu.data.DataService
import org.davidd.menu.data.InMemoryDataService
import org.davidd.menu.view.OrdersActivity
import org.davidd.menu.viewmodel.OrdersViewModel
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    @Override
    override fun onCreate() {
        super.onCreate()

        DaggerMainApplicationComponent.builder()
                .create(this)
                .inject(this)
    }

    @Override
    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}

// responsible for injecting the MainApplication class
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        MainApplicationModule::class))
interface MainApplicationComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MainApplication>()
}

// creates an Injector for specific classes (for the return types used in methods annotated with @ContributesAndroidInjector)
@Module(includes = [DataLayerModule::class, ViewModelModule::class])
abstract class MainApplicationModule {

    // use this to avoid IllegalArgumentException: No injector factory bound for Class<org.davidd.menu.view.OrdersActivity>
    @ContributesAndroidInjector
    abstract fun contributeOrdersActivityInjector(): OrdersActivity
}

@Module
class DataLayerModule {

    @Provides
    fun provideDataService(): DataService = InMemoryDataService
}

// TODO why do we need this if we create the specific VM in Activity/Fragment?
@Module
abstract class ViewModelModule {

    // returns the specified instance from parameter
    @Binds
    @IntoMap
    @AndroidInjectionKey("OrdersViewModel")
    abstract fun provideOrdersViewModel(viewModel: OrdersViewModel): ViewModel
}
