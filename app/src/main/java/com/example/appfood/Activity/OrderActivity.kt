package com.example.appfood.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfood.Adapter.OrderAdapter
import com.example.appfood.Domain.OrderModel
import com.example.appfood.R
import com.google.firebase.database.*

class OrderActivity : AppCompatActivity() {
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderList: MutableList<OrderModel>
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        orderRecyclerView = findViewById(R.id.order_recycler_view)
        orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderRecyclerView.setHasFixedSize(true)

        orderList = mutableListOf()
        orderAdapter = OrderAdapter(orderList)
        orderRecyclerView.adapter = orderAdapter

        database = FirebaseDatabase.getInstance()
        val ordersRef = database.getReference("Orders")

        ordersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    orderList.clear()
                    for (orderSnapshot in snapshot.children) {
                        val order = orderSnapshot.getValue(OrderModel::class.java)
                        order?.let {
                            orderList.add(it)
                        }
                    }
                    orderAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }
}