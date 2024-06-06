package com.example.appfood.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appfood.Domain.OrderModel
import com.example.appfood.R

class OrderAdapter(private val orderList: List<OrderModel>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdTextView: TextView = itemView.findViewById(R.id.order_id_text_view)
        val orderStatusTextView: TextView = itemView.findViewById(R.id.order_status_text_view)
        // Add more views as needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.orderIdTextView.text = currentItem.orderID
        holder.orderStatusTextView.text = currentItem.status
        // Bind other data as needed
    }

    override fun getItemCount() = orderList.size
}