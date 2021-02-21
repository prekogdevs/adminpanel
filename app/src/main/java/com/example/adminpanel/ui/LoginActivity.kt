package com.example.adminpanel.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.adminpanel.R
import com.example.adminpanel.api.model.Admin
import com.example.adminpanel.data.AdminViewModel
import com.example.adminpanel.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val adminViewModel by lazy {
        ViewModelProvider(this).get(AdminViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        adminViewModel.adminResponse.observe(this, {
            if (it != null) {
                val intent = Intent(this, CustomerActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, getString(R.string.invalid_login_toast_text), Toast.LENGTH_LONG).show()
            }
        })

        binding.loginButton.setOnClickListener {
            adminViewModel.login(
                Admin(
                    binding.inputEdtUsername.text.toString(),
                    binding.inputEdtPassword.text.toString()
                )
            )
        }
        setContentView(binding.root)
    }
}