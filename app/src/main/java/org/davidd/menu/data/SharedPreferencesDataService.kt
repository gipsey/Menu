package org.davidd.menu.data

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.davidd.menu.common.MainApplication
import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders

object SharedPreferencesDataService : DataService {

    private val KEY = "ORDERS"

    override fun addOrder(order: Order, callback: DataServiceCallback<Unit>) {
        GlobalScope.launch {
            val newOrdersList = ArrayList<Order>()
            val ordersJson = PreferenceManager.getDefaultSharedPreferences(context()).getString(KEY, "")

            if (ordersJson.isEmpty().not()) {
                val ordersObject = Gson().fromJson<Orders>(ordersJson, Orders::class.java)
                newOrdersList.addAll(ordersObject.orders)
            }

            newOrdersList.add(order)
            val newJsonString = Gson().toJson(Orders(newOrdersList))

            PreferenceManager.getDefaultSharedPreferences(context()).edit().putString(KEY, newJsonString).commit()
            callback.onLoadSucceeded(Unit)
        }
    }

    override fun removeOrder(id: kotlin.Int, callback: DataServiceCallback<Unit>) {
        GlobalScope.launch {
            val newOrdersList = ArrayList<Order>()
            val ordersJson = PreferenceManager.getDefaultSharedPreferences(context()).getString(KEY, "")

            if (ordersJson.isEmpty().not()) {
                val ordersObject = Gson().fromJson<Orders>(ordersJson, Orders::class.java)
                newOrdersList.addAll(ordersObject.orders)
            }

            for (order in newOrdersList) {
                if (order.id == id) {
                    newOrdersList.remove(order)
                    break
                }
            }

            val newJsonString = Gson().toJson(Orders(newOrdersList))
            PreferenceManager.getDefaultSharedPreferences(context()).edit().putString(KEY, newJsonString).commit()
            callback.onLoadSucceeded(Unit)
        }
    }

    override fun getOrders(callback: DataServiceCallback<Orders>) {
        GlobalScope.launch {
            val ordersJson = PreferenceManager.getDefaultSharedPreferences(context()).getString(KEY, "")

            if (ordersJson.isEmpty()) {
                callback.onLoadSucceeded(Orders(ArrayList()))
            } else {
                val ordersObject = Gson().fromJson<Orders>(ordersJson, Orders::class.java)
                callback.onLoadSucceeded(ordersObject)
            }
        }
    }

    private fun context(): Context {
        return MainApplication.applicationContext()
    }
}