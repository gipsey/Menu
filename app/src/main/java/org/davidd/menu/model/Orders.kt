package org.davidd.menu.model

import com.google.gson.annotations.SerializedName

data class Orders(
        @SerializedName("orders") val orders: ArrayList<Order>
)