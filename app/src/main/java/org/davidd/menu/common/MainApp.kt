package org.davidd.menu.common

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.content.Context
import dagger.*
import dagger.android.*
import dagger.multibindings.IntoMap
import org.davidd.menu.data.DataService
import org.davidd.menu.data.InMemoryDataService
import org.davidd.menu.view.OrdersActivity
import org.davidd.menu.viewmodel.OrdersViewModel
import javax.inject.Inject

class MainApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    companion object {
        private var instance: MainApp? = null

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

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    @Override
    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}

@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        BindingModule::class,
        TestModule::class))
interface AppComponent : AndroidInjector<MainApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}

@Module
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application

    @Provides
    fun provideDataService(): DataService = InMemoryDataService
}

// creates an Injector for specific classes (for the return types used in methods annotated with @ContributesAndroidInjector)
@Module(includes = [ViewModelModule::class])
abstract class BindingModule {

    // use this to avoid IllegalArgumentException: No injector factory bound for Class<org.davidd.menu.view.OrdersActivity>
    @ContributesAndroidInjector(modules = [OrdersActivityModule::class])
    abstract fun contributeOrdersActivityInjector(): OrdersActivity
}

// TODO why do we need this if we create the specific VM in Activity/Fragment?
// to be injectable in other classes if there was created (create in Activity and inject in separate Fragments ? )

//1. Why do we need ViewModelModule if we create the specific VM in Activity/Fragment?
//2. Why to store VMs in a map?
@Module
abstract class ViewModelModule {

    // returns the specified instance from parameter
    @Binds
    @IntoMap
    @AndroidInjectionKey("OrdersViewModel")
    abstract fun provideOrdersViewModel(viewModel: OrdersViewModel): ViewModel
}

@Module
abstract class OrdersActivityModule {

    @Binds
    abstract fun provideTestClassB(testClassB: TestClassB): ITestClassB
}

@Module
class TestModule {

    @Provides
    fun provideA(context: Context) = TestClassA()
}

class TestClassA {

}

interface ITestClassB {

}

class TestClassB @Inject constructor(context: Context) : ITestClassB {

}
