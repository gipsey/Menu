package org.davidd.menu.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import org.davidd.menu.data.DataService

class OrdersViewModelFactory(private val dataService: DataService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            try {
                val declaredConstructor = modelClass.getConstructor(DataService::class.java)

                return declaredConstructor.newInstance(dataService) as T
            } catch (e: Exception) {
                throw IllegalArgumentException("could not create ViewModel instance", e)
            }
        } else {
            throw IllegalArgumentException("incompatible types")
        }
    }
}