package org.davidd.menu.common

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.*
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

        DaggerMainApplicationComponent.create().inject(this)
    }

    @Override
    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}

@Module
abstract class MainApplicationModule {

    @ContributesAndroidInjector // TODO ???
    abstract fun contributeActivityInjector(): OrdersActivity
}

// responsible for injecting the MainApplication class
@Component(modules = arrayOf(AndroidInjectionModule::class, MainApplicationModule::class, OrdersActivityModule::class))
interface MainApplicationComponent : AndroidInjector<MainApplication>

@Module
class OrdersActivityModule(val dataService: DataService = InMemoryDataService) {

    @Provides
    fun provideOrdersViewModel(): OrdersViewModel {
//        ordersViewModel = ViewModelProviders.of(this, OrdersViewModelFactory(InMemoryDataService)).get(OrdersViewModel::class.java)
        return OrdersViewModel(dataService) // TODO this is bad
    }
}

