package com.example.appfood.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.app.Domain.Foods
import com.example.appfood.Helper.ManagmentCart
import com.example.appfood.R
import com.example.appfood.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var foodItem: Foods
    private var num = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.black)

        getIntentExtra()
        setVariable()
    }

    private fun setVariable() {
        managmentCart = ManagmentCart(this)
        binding.backBtn.setOnClickListener { finish() }

        Glide.with(this)
            .load(foodItem.ImagePath)
            .into(binding.pic)

        binding.priceTxt.text = "$${foodItem.Price}"
        binding.titleTxt.text = foodItem.Title
        binding.descriptionTxt.text = foodItem.Description
        binding.rateTxt.text = "${foodItem.Star} Rating"
        binding.ratingBar2.rating = foodItem.Star.toFloat()
        binding.totalTxt.text = "$${num * foodItem.Price}"

        binding.plusBtn.setOnClickListener {
            num += 1
            binding.numTxt.text = num.toString()
            binding.totalTxt.text = "$${num * foodItem.Price}"
        }

        binding.minusBtn.setOnClickListener {
            if (num > 1) {
                num -= 1
                binding.numTxt.text = num.toString()
                binding.totalTxt.text = "$${num * foodItem.Price}"
            }
        }

        binding.addBtn.setOnClickListener {
            foodItem.NumberInCart = num
            managmentCart.insertFood(foodItem)
        }
    }

    private fun getIntentExtra() {
        foodItem = intent.getSerializableExtra("object") as Foods
    }
}
