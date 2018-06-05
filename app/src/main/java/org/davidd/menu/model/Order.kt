package org.davidd.menu.model

import com.google.gson.annotations.SerializedName

data class Order(
        @SerializedName("id") val id: kotlin.Int,
        @SerializedName("name") val name: String
)