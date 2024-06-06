package com.example.appfood.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.appfood.R
import com.example.appfood.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVariable()
    }

    private fun setVariable() {
        binding.loginBtn.setOnClickListener {
            val email = binding.userEdt.text.toString()
            val password = binding.passEdt.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        } else {
                            Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else {
                Toast.makeText(this@LoginActivity, "Please fill username and password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.SignUpTran.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
    }
}
