package com.example.appfood.Domain

import com.example.app.Domain.Foods
import java.io.Serializable

data class OrderModel(
    var orderID: String = "",
    var userID: String = "",
    var listItem: ArrayList<Foods> = ArrayList(),
    var orderDateTime: String = "",
    var status: String = "",
    var userEmail: String=""
): Serializable
