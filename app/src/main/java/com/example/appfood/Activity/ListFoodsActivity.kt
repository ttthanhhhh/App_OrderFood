package com.example.appfood.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Domain.Foods
import com.example.appfood.Adapter.FoodListAdapter
import com.example.appfood.R
import com.example.appfood.databinding.ActivityListFoodsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class ListFoodsActivity : BaseActivity() {
    private lateinit var binding: ActivityListFoodsBinding
    private lateinit var adapterListFood: RecyclerView.Adapter<*>
    private var CategoryId = 0
    private var CategoryName: String? = null
    private var searchText: String? = null
    private var isSearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentExtra()
        initList()
        setVariable()
    }

    private fun setVariable() {
        // Implement your variable setting logic here
    }

    private fun initList() {
        val myRef = database.getReference("Foods")
        binding.progressBar.visibility = View.VISIBLE
        val list = ArrayList<Foods>()

        val query = if (isSearch) {
            myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff')
        } else {
            myRef.orderByChild("CategoryId").equalTo(CategoryId.toDouble())
        }

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(Foods::class.java)!!)
                    }

                    if (list.isNotEmpty()) {
                        binding.foodListView.layoutManager = GridLayoutManager(this@ListFoodsActivity, 2)
                        adapterListFood = FoodListAdapter(list)
                        binding.foodListView.adapter = adapterListFood
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }

    private fun getIntentExtra() {
        CategoryId = intent.getIntExtra("CategoryId", 0)
        CategoryName = intent.getStringExtra("CategoryName")
        searchText = intent.getStringExtra("text")
        isSearch = intent.getBooleanExtra("isSearch", false)

        binding.titleTxt.text = CategoryName
        binding.backBtn.setOnClickListener { finish() }
    }
}
