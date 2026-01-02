package com.latihan.project_tugas_android_akhir

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.latihan.project_tugas_android_akhir.databinding.ActivityLoginBinding
import com.latihan.project_tugas_android_akhir.utils.PreferencesManager

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferencesManager: PreferencesManager
    
    companion object {
        private const val VALID_USERNAME = "aninda"
        private const val VALID_PASSWORD = "aninda123"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferencesManager = PreferencesManager.getInstance(this)
        
        setupListeners()
    }
    
    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            
            when {
                username.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, R.string.login_empty, Toast.LENGTH_SHORT).show()
                }
                username == VALID_USERNAME && password == VALID_PASSWORD -> {
                    // Login berhasil
                    preferencesManager.saveLoginStatus(true)
                    preferencesManager.saveUsername(username)
                    
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
