package org.davidd.menu.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.content_orders.*
import org.davidd.menu.R
import org.davidd.menu.data.InMemoryDataService
import org.davidd.menu.viewmodel.OrdersViewModel
import org.davidd.menu.viewmodel.OrdersViewModelFactory

class OrdersActivity : AppCompatActivity() {

    private companion object {
        val TAG = OrdersActivity::class.java.simpleName
    }

    private lateinit var ordersViewModel: OrdersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        setSupportActionBar(toolbar)

        val orderIdEditText = findViewById<EditText>(R.id.order_id)
        val addOrderButton = findViewById<Button>(R.id.add_order)

        addOrderButton.setOnClickListener {
            ordersViewModel.addNewOrder(orderIdEditText.text.toString())
        }

        ordersViewModel = ViewModelProviders.of(this, OrdersViewModelFactory(InMemoryDataService)).get(OrdersViewModel::class.java)
        ordersViewModel.getOrdersLiveData().observe(this, Observer<String> { t ->
            Log.d(TAG, "onChanged")
            orderIdEditText.text = null
            orders_list.text = t
        })

        ordersViewModel.getMessageLiveData().observe(this, Observer<String> { s ->
            Toast.makeText(this, s, Toast.LENGTH_LONG).show()
        })
    }
}
