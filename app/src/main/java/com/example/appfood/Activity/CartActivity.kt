package com.example.appfood.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfood.Adapter.CartAdapter
import com.example.appfood.Domain.OrderModel
import com.example.appfood.Helper.ChangeNumberItemsListener
import com.example.appfood.Helper.ManagmentCart
import com.example.appfood.R
import com.example.appfood.databinding.ActivityCartBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var managmentCart: ManagmentCart
    private var tax = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        setVariable()
        calculateCart()
        initList()
    }

    private fun initList() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.visibility = View.VISIBLE
            binding.scrollviewCart.visibility = View.GONE
        } else {
            binding.emptyTxt.visibility = View.GONE
            binding.scrollviewCart.visibility = View.VISIBLE
        }
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.cardView.layoutManager = linearLayoutManager
        adapter = CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
            override fun change() {
                calculateCart()
            }
        })
        binding.cardView.adapter = adapter
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0

        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100.0
        val total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100.0
        val itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100.0

        binding.totalFeeTxt.text = "$$itemTotal"
        binding.taxTxt.text = "$$tax"
        binding.deliveryTxt.text = "$$delivery"
        binding.totalTxt.text = "$$total"
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
        binding.orderBtn.setOnClickListener {
            placeOrder()
        }

    }
    private fun placeOrder() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Please log in to place an order", Toast.LENGTH_SHORT).show()
            return
        }

        val userID = currentUser.uid
        val userEmail = currentUser.email ?: ""
        val orderID = FirebaseDatabase.getInstance().reference.push().key ?: UUID.randomUUID().toString()
        val listItem = managmentCart.getListCart()
        val orderDateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(
            Date()
        )
        val status = "Complete"

        val order = OrderModel(
            orderID = orderID,
            userID = userID,
            listItem = listItem,
            orderDateTime = orderDateTime,
            status = status,
            userEmail = userEmail
        )

        val database = FirebaseDatabase.getInstance()
        val orderRef = database.getReference("Orders")

        orderRef.child(orderID).setValue(order)
            .addOnSuccessListener {
                Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show()
                // You can clear the cart or navigate to another activity if needed
                managmentCart.clearCart()
                val intent = Intent(this@CartActivity, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, ": ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
