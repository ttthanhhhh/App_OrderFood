package com.example.appfood.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.appfood.Activity.DetailActivity
import com.example.app.Domain.Foods
import com.example.appfood.R

class FoodListAdapter(private val items: ArrayList<Foods>) : RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.viewholder_list_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleTxt.text = item.Title
        holder.timeTxt.text = "${item.TimeValue}min"
        holder.priceTxt.text = "$ ${item.Price}"
        holder.rateTxt.text = item.Star.toString()

        Glide.with(context)
            .load(item.ImagePath)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(holder.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.titleTxt)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)
        val rateTxt: TextView = itemView.findViewById(R.id.rateTxt)
        val timeTxt: TextView = itemView.findViewById(R.id.timeTxt)
        val pic: ImageView = itemView.findViewById(R.id.img)
    }
}
