package org.davidd.menu.data

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import org.davidd.menu.MainApplication
import org.davidd.menu.model.Order
import org.davidd.menu.model.Orders

class SharedPreferencesDataService() : DataService {

    private val KEY: String = "ORDERS"
    private val context: Context = MainApplication.applicationContext()

    override fun addOrder(order: Order, callback: DataServiceCallback<Unit>) {
        val newOrdersList = ArrayList<Order>()
        val ordersJson = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY, "")

        if (ordersJson.isEmpty().not()) {
            val ordersObject = Gson().fromJson<Orders>(ordersJson, Orders::class.java)
            newOrdersList.addAll(ordersObject.orders)
        }

        newOrdersList.add(order)
        val newJsonString = Gson().toJson(Orders(newOrdersList))

        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY, newJsonString).commit()
        callback.onLoadSucceeded(Unit)
    }

    override fun removeOrder(id: kotlin.Int, callback: DataServiceCallback<Unit>) {
        val newOrdersList = ArrayList<Order>()
        val ordersJson = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY, "")

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
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY, newJsonString).commit()
        callback.onLoadSucceeded(Unit)
    }

    override fun getOrders(callback: DataServiceCallback<Orders>) {
        val ordersJson = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY, "")

        if (ordersJson.isEmpty()) {
            callback.onLoadSucceeded(Orders(ArrayList()))
        } else {
            val ordersObject = Gson().fromJson<Orders>(ordersJson, Orders::class.java)
            callback.onLoadSucceeded(ordersObject)
        }
    }
}