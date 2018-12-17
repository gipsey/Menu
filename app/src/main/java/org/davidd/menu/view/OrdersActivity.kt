package org.davidd.menu.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_orders.*
import org.davidd.menu.R
import org.davidd.menu.data.InMemoryDataService
import org.davidd.menu.data.SharedPreferencesDataService
import org.davidd.menu.model.Orders
import org.davidd.menu.viewmodel.OrdersViewModel
import org.davidd.menu.viewmodel.OrdersViewModelFactory

class OrdersActivity : AppCompatActivity() {

    companion object {
        private val TAG = OrdersActivity::class.java.simpleName
    }

    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var ordersListTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        setSupportActionBar(toolbar)

        val orderIdEditText = findViewById<EditText>(R.id.order_id)
        val addOrderButton = findViewById<Button>(R.id.add_order)

        addOrderButton.setOnClickListener {
            ordersViewModel.addNewOrder(orderIdEditText.text.toString())
        }

        ordersListTextView = findViewById(R.id.orders_list)

        ordersViewModel = ViewModelProviders.of(this, OrdersViewModelFactory(InMemoryDataService)).get(OrdersViewModel::class.java)
        ordersViewModel.getOrdersLiveData().observe(this, Observer<Orders> { t ->
            Log.d(TAG, "onChanged")
            orderIdEditText.text = null

            if (t != null) {
                var text = ""

                for (i in t.orders) {
                    text += i.id.toString() + " "
                }

                ordersListTextView.text = text
            }
        })

        ordersViewModel.getMessageLiveData().observe(this, Observer<String> { s ->
            Toast.makeText(this, s, Toast.LENGTH_LONG).show()
        })
    }
}
