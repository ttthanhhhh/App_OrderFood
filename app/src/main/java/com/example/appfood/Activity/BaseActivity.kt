package com.example.appfood.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

open class BaseActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    val TAG = "uilove"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()

        window.statusBarColor = resources.getColor(R.color.white)
    }
}
