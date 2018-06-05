package org.davidd.menu.data

import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders

interface DataService {

    fun addOrder(order: Order, callback: DataServiceCallback<Unit>)
    fun removeOrder(id: kotlin.Int, callback: DataServiceCallback<Unit>)
    fun getOrders(callback: DataServiceCallback<Orders>)
}