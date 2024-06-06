package com.example.appfood.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appfood.Activity.ListFoodsActivity
import com.example.appfood.Domain.Category
import com.example.appfood.R

class CategoryAdapter(private val items: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = items[position].Name

        val resourceId = when (position) {
            0 -> R.drawable.cat_1_background
            1 -> R.drawable.cat_2_background
            2 -> R.drawable.cat_3_background
            3 -> R.drawable.cat_4_background
            4 -> R.drawable.cat_5_background
            5 -> R.drawable.cat_6_background
            6 -> R.drawable.cat_7_background
            7 -> R.drawable.cat_8_background
            else -> R.drawable.default_background
        }
        holder.pic.setBackgroundResource(resourceId)

        val drawableResourceName = items[position].ImagePath
        val drawableResourceId = context.resources.getIdentifier(drawableResourceName, "drawable", holder.itemView.context.packageName)

        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ListFoodsActivity::class.java)
            intent.putExtra("CategoryId", items[position].Id)
            intent.putExtra("CategoryName", items[position].Name)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.catNameTxt)
        val pic: ImageView = itemView.findViewById(R.id.imgCat)
    }
}
