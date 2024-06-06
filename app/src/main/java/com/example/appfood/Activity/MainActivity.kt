package com.example.appfood.Activity

//import com.example.appfood.Adapter.CategoryAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Domain.Foods
import com.example.appfood.Adapter.BestFoodsAdapter
import com.example.appfood.Adapter.CategoryAdapter
import com.example.appfood.Domain.Category
import com.example.appfood.Domain.Location
import com.example.appfood.Domain.Price
import com.example.appfood.Domain.Time
import com.example.appfood.R
import com.example.appfood.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLocation()
        initTime()
        initPrice()
        initBestFood()
        initCategory()
        setVariable()



    }
    private fun setVariable() {
        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
        binding.searchBtn.setOnClickListener {
            val text = binding.searchEdt.text.toString()
            if (text.isNotEmpty()) {
                val intent = Intent(this@MainActivity, ListFoodsActivity::class.java)
                intent.putExtra("text", text)
                intent.putExtra("isSearch", true)
                startActivity(intent)
            }
        }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }


    private fun initBestFood() {
        val myRef = database.getReference("Foods")
        binding.progressBarBestFood.visibility = View.VISIBLE
        val list = ArrayList<Foods>()
        val query: Query = myRef.orderByChild("BestFood").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Foods::class.java)!!)
                    }
                    if (list.isNotEmpty()) {
                        binding.bestFoodView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        val adapter: RecyclerView.Adapter<*> = BestFoodsAdapter(list)
                        binding.bestFoodView.adapter = adapter
                    }
                    binding.progressBarBestFood.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý sự kiện onCancelled nếu cần
            }
        })
    }


    private fun initLocation() {
        val myRef: DatabaseReference = database.getReference("Location")
        val list: ArrayList<Location> = ArrayList()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Location::class.java)!!)
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.locationSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun initTime() {
        val myRef: DatabaseReference = database.getReference("Time")
        val list: ArrayList<Time> = ArrayList()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Time::class.java)!!)
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.priceSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initPrice() {
        val myRef: DatabaseReference = database.getReference("Price")
        val list: ArrayList<Price> = ArrayList()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Price::class.java)!!)
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.timeSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun initCategory() {
        val myRef = database.getReference("Category")
        binding.progressBarCategory.visibility = View.VISIBLE
        val list = ArrayList<Category>()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Category::class.java)!!)
                    }
                    if (list.size > 0) {
                        binding.categoryView.layoutManager = GridLayoutManager(this@MainActivity, 4)
                        val adapter = CategoryAdapter(list)
                        binding.categoryView.adapter = adapter
                    }
                    binding.progressBarCategory.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }



}
