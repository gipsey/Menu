package org.davidd.menu.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import org.davidd.menu.data.DataService
import org.davidd.menu.data.DataServiceCallback
import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders
import org.davidd.menu.view.OrdersActivity

/**
 * Creates LiveData intance and fetches / updates data from service / DB.
 */
class OrdersRepo private constructor(private val dataService: DataService) {

    private val ordersLiveData: MutableLiveData<Orders> = MutableLiveData()

    fun getOrdersLiveData(): LiveData<Orders> {
        Log.d(TAG, "getOrdersLiveData")
        return ordersLiveData
    }

    fun fetchOrders() {
        Log.d(TAG, "fetchOrders")

        dataService.getOrders(object : DataServiceCallback<Orders> {
            override fun onLoadFailed() {
                Log.d(TAG, "fetchOrders fail")
            }

            override fun onLoadSucceeded(data: Orders) {
                Log.d(TAG, "fetchOrders success")
                ordersLiveData.postValue(data)
            }
        })
    }

    fun addNewOrder(order: Order) {
        Log.d(TAG, "addNewOrder")

        dataService.addOrder(order, object : DataServiceCallback<Unit> {
            override fun onLoadFailed() {
                Log.d(TAG, "addNewOrder fail")
            }

            override fun onLoadSucceeded(data: Unit) {
                Log.d(TAG, "addNewOrder success")

                val orders = ordersLiveData.value!!
                orders.orders.add(order)

                ordersLiveData.postValue(orders)
            }
        })
    }

    companion object {

        private val TAG = OrdersActivity::class.java.simpleName
        private var ins: OrdersRepo? = null

        fun getInstance(dataService: DataService): OrdersRepo {
            if (ins == null) {
                synchronized(this) {
                    if (ins == null) {
                        ins = OrdersRepo(dataService)
                    }

                }
            }

            return ins as OrdersRepo
        }
    }
}