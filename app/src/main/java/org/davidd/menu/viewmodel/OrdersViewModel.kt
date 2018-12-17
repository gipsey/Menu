package org.davidd.menu.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import org.davidd.menu.data.InMemoryDataService
import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders
import org.davidd.menu.repo.OrdersRepo

class OrdersViewModel : ViewModel() {

    private val ordersLiveData: MutableLiveData<Orders>
    // todo use constuctor
    private val ordersRepo: OrdersRepo = OrdersRepo.getInstance(InMemoryDataService())

    init {
        Log.d("view model", "init - not called at orientation change")
        ordersLiveData = ordersRepo.getOrders()
        ordersRepo.fetchOrders()
    }

    fun getOrders(): MutableLiveData<Orders> {
        Log.d("view model", "getOrders")
        return ordersLiveData
    }

    fun addNewOrder(orderName: Int) {
        Log.d("view model", "addNewOrder")
        ordersRepo.addNewOrder(Order(orderName, orderName.toString()))
    }
}