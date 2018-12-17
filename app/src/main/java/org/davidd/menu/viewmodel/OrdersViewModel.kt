package org.davidd.menu.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import android.util.Log
import org.davidd.menu.data.DataService
import org.davidd.menu.data.DataServiceCallback
import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders

class OrdersViewModel(private val dataService: DataService) : ViewModel() {

    companion object {
        private val TAG = OrdersViewModel::class.java.simpleName
    }

    private val ordersLiveData: MutableLiveData<Orders> = MutableLiveData()
    private val messageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        Log.d(TAG, "init")
        dataService.getOrders(object : DataServiceCallback<Orders> {
            override fun onLoadFailed() {
                messageLiveData.postValue("preparing orders failed")
            }

            override fun onLoadSucceeded(data: Orders) {
                ordersLiveData.postValue(data)
            }
        })
    }

    fun getOrdersLiveData(): LiveData<Orders> {
        return ordersLiveData
    }

    fun getMessageLiveData(): LiveData<String> {
        return messageLiveData
    }

    fun addNewOrder(orderName: String) {
        if (TextUtils.isEmpty(orderName)) {
            messageLiveData.postValue("invalid order id")
            return
        }

        val order = Order(orderName.toInt(), orderName)

        dataService.addOrder(order, object : DataServiceCallback<Unit> {
            override fun onLoadFailed() {
                messageLiveData.postValue("creation of order failed")
            }

            override fun onLoadSucceeded(data: Unit) {
                val value = ordersLiveData.value!!
                value.orders.add(order)

                ordersLiveData.postValue(value)
            }
        })
    }
}