package org.davidd.menu.data.api

import android.app.IntentService
import android.content.Intent
import android.util.Log
import org.davidd.menu.data.SharedPreferencesDataService
import org.davidd.menu.generateOrderId
import org.davidd.menu.model.Order
import org.davidd.menu.repo.OrdersRepo

class OrdersFetcherService : IntentService("OrdersFetcherService") {

    override fun onHandleIntent(intent: Intent?) {
        while (true) {
            Thread.sleep(3000)

            val id = generateOrderId()
            val order = Order(id.toInt(), id.toString())

            OrdersRepo.getInstance(SharedPreferencesDataService()).addNewOrder(order)
        }
    }
}
