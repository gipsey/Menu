package org.davidd.menu.data

import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders
import java.util.*

object InMemoryDataService : DataService {

    private val ordersObject = Orders(ArrayList())

    override fun addOrder(order: Order, callback: DataServiceCallback<Unit>) {
        Thread.sleep(2000)

        ordersObject.orders.add(order)

        callback.onLoadSucceeded(Unit)
    }

    override fun removeOrder(id: kotlin.Int, callback: DataServiceCallback<Unit>) {
        Thread.sleep(2000)

        for (order in ordersObject.orders) {
            if (order.id.equals(id)) {
                ordersObject.orders.remove(order)
                callback.onLoadSucceeded(Unit)
                return
            }
        }

        callback.onLoadFailed()
    }

    override fun getOrders(callback: DataServiceCallback<Orders>) {
        Thread.sleep(2000)

        callback.onLoadSucceeded(ordersObject)
    }
}