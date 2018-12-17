package org.davidd.menu.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_orders.*
import org.davidd.menu.R
import org.davidd.menu.data.InMemoryDataService
import org.davidd.menu.model.Orders
import org.davidd.menu.repo.OrdersRepo
import org.davidd.menu.viewmodel.OrdersViewModel
import org.davidd.menu.viewmodel.OrdersViewModelFactory

class OrdersActivity : AppCompatActivity(), Observer<Orders> {

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
            val text = orderIdEditText.text.toString()
            ordersViewModel.addNewOrder(text.toInt())

            orderIdEditText.text = null
        }

        ordersListTextView = findViewById(R.id.orders_list)

        val ordersRepo: OrdersRepo = OrdersRepo.getInstance(InMemoryDataService())
        ordersViewModel = ViewModelProviders.of(this, OrdersViewModelFactory(ordersRepo)).get(OrdersViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        ordersViewModel.getOrders().observe(this, this)
    }

    override fun onChanged(t: Orders?) {
        Log.d(TAG, "onChanged")

        if (t == null) {
            return
        }

        var text = ""

        // todo  Caused by: java.util.ConcurrentModificationException
        for (i in t.orders) {
            text += i.id.toString() + " "
        }

        ordersListTextView.text = text
    }
}
