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
    private lateinit var auth: com.google.firebase.auth.FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        preferencesManager = PreferencesManager.getInstance(this)
        auth = com.google.firebase.auth.FirebaseAuth.getInstance()
        
        setupListeners()
    }
    
    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextUsername.text.toString().trim() // Using email as username
            val password = binding.editTextPassword.text.toString().trim()
            
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.login_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase Login
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        val user = auth.currentUser
                        preferencesManager.saveLoginStatus(true)
                        preferencesManager.saveUsername(user?.displayName ?: email)
                        
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Login Gagal: ${task.exception?.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.tvRegisterLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.buttonLihatMateri.setOnClickListener {
            startActivity(Intent(this, MateriActivity::class.java))
        }
    }
}
