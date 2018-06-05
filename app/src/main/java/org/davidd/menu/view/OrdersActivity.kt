package org.davidd.menu.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_orders.*
import org.davidd.menu.R
import org.davidd.menu.model.Orders
import org.davidd.menu.viewmodel.OrdersViewModel

class OrdersActivity : AppCompatActivity(), Observer<Orders> {

    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var ordersListTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        setSupportActionBar(toolbar)

        val orderIdEditText = findViewById<EditText>(R.id.order_id)
        val addOrderButton = findViewById<Button>(R.id.add_order)

        addOrderButton.setOnClickListener {
            val text = orderIdEditText.text.toString()
            ordersViewModel.addNewOrder(text.toInt())

            orderIdEditText.text = null
        }

        ordersListTextView = findViewById(R.id.orders_list)
        ordersViewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(OrdersViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        ordersViewModel.getOrders().observe(this, this)
    }

    override fun onChanged(t: Orders?) {
        if (t == null) {
            return
        }

        var text = ""

        for (i in t.orders) {
            text += i.id.toString() + " "
        }

        ordersListTextView.text = text
    }
}
