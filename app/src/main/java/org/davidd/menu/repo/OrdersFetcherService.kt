package org.davidd.menu.repo

import android.app.IntentService
import android.content.Intent
import org.davidd.menu.common.generateOrderId
import org.davidd.menu.data.SharedPreferencesDataService
import org.davidd.menu.model.Order

class OrdersFetcherService : IntentService("OrdersFetcherService") {

    override fun onHandleIntent(intent: Intent?) {
        while (true) {
            Thread.sleep(2000)

            val id = generateOrderId()
            val order = Order(id.toInt(), id.toString())

            OrdersRepo.getInstance(SharedPreferencesDataService()).addNewOrder(order)
        }
    }
}
