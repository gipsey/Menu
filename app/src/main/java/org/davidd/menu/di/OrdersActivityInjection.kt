package org.davidd.menu.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import org.davidd.menu.view.OrdersActivity

@Subcomponent(modules = arrayOf())
interface OrdersActivitySubcomponent : AndroidInjector<OrdersActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<OrdersActivity>()
}

@Module(subcomponents = arrayOf(OrdersActivitySubcomponent::class))
abstract class OrdersActivityModule {

    @Binds
    @IntoMap
    @ClassKey(OrdersActivity::class)
    abstract fun bind(builder: OrdersActivitySubcomponent.Builder): AndroidInjector.Factory<*>
}

@Component(modules = arrayOf(OrdersActivityModule::class))
interface MainApplicationComponent {

}

