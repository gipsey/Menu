package org.davidd.menu.repo

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import org.davidd.menu.data.DataService
import org.davidd.menu.data.DataServiceCallback
import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders

/**
 * Creates LiveData intance and fetches / updates data from service / DB.
 * todo remove livedata
 */
class OrdersRepo private constructor(private val dataService: DataService) {

    private val ordersLiveData: MutableLiveData<Orders> = MutableLiveData()

    fun getOrders(): MutableLiveData<Orders> {
        Log.d("repo", "getOrders")
        return ordersLiveData
    }

    fun fetchOrders() {
        Log.d("repo", "fetchOrders")

        dataService.getOrders(object : DataServiceCallback<Orders> {
            override fun onLoadFailed() {
                Log.d("repo", "fetchOrders fail")
            }

            override fun onLoadSucceeded(data: Orders) {
                Log.d("repo", "fetchOrders success")
                ordersLiveData.postValue(data)
            }
        })
    }

    fun addNewOrder(order: Order) {
        Log.d("repo", "addNewOrder")

        dataService.addOrder(order, object : DataServiceCallback<Unit> {
            override fun onLoadFailed() {
                Log.d("repo", "addNewOrder fail")
            }

            override fun onLoadSucceeded(data: Unit) {
                Log.d("repo", "addNewOrder success")

                val orders = ordersLiveData.value!!
                orders.orders.add(order)

                ordersLiveData.postValue(orders)
            }
        })
    }

    companion object {

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