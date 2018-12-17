package org.davidd.menu.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.davidd.menu.repo.OrdersRepo

class OrdersViewModelFactory(private val ordersRepo: OrdersRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            try {
                val declaredConstructor = modelClass.getConstructor(OrdersRepo::class.java)

                return declaredConstructor.newInstance(ordersRepo) as T
            } catch (e: Exception) {
                throw IllegalArgumentException("could not create ViewModel instance", e)
            }
        } else {
            throw IllegalArgumentException("incompatible types")
        }
    }
}