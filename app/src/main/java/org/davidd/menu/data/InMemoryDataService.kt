package org.davidd.menu.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders

object InMemoryDataService : DataService {

    private val ordersObject = Orders(ArrayList())

    override fun addOrder(order: Order, callback: DataServiceCallback<Unit>) {
        GlobalScope.launch {
            Thread.sleep(2000)

            ordersObject.orders.add(order)

            callback.onLoadSucceeded(Unit)
        }
    }

    override fun removeOrder(id: kotlin.Int, callback: DataServiceCallback<Unit>) {
        GlobalScope.launch {
            Thread.sleep(2000)

            var isSuccess = false

            for (order in ordersObject.orders) {
                if (order.id.equals(id)) {
                    ordersObject.orders.remove(order)
                    callback.onLoadSucceeded(Unit)
                    isSuccess = true
                    break
                }
            }

            if (!isSuccess) {
                callback.onLoadFailed()
            }
        }
    }

    override fun getOrders(callback: DataServiceCallback<Orders>) {
        GlobalScope.launch {
            Thread.sleep(2000)

            val orders = Orders(ArrayList(ordersObject.orders))
            callback.onLoadSucceeded(orders)
        }
    }
}