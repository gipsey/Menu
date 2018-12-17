package org.davidd.menu.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders
import org.davidd.menu.repo.OrdersRepo

class OrdersViewModel(private val ordersRepo: OrdersRepo) : ViewModel() {

    companion object {
        private val TAG = OrdersViewModel::class.java.simpleName
    }

    private val ordersLiveData: LiveData<Orders>

    init {
        Log.d(TAG, "init")

        ordersLiveData = ordersRepo.getOrdersLiveData()
        ordersRepo.fetchOrders()
    }

    fun getOrders(): LiveData<Orders> {
        Log.d(TAG, "getOrdersLiveData")

        return ordersLiveData
    }

    fun addNewOrder(orderName: Int) {
        Log.d(TAG, "addNewOrder")

        ordersRepo.addNewOrder(Order(orderName, orderName.toString()))
    }
}