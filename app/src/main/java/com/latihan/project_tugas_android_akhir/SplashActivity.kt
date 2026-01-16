package com.latihan.project_tugas_android_akhir

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.latihan.project_tugas_android_akhir.utils.PreferencesManager

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    
    private lateinit var preferencesManager: PreferencesManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        preferencesManager = PreferencesManager.getInstance(this)
        
        // Keep splash screen visible for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginStatus()
        }, 2000)
    }
    
    private fun checkLoginStatus() {
        val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
        
        val intent = if (currentUser != null) {
            // User sudah login di Firebase, langsung ke MainActivity
            Intent(this, MainActivity::class.java)
        } else {
            // User belum login atau session habis, ke LoginActivity
            Intent(this, LoginActivity::class.java)
        }
        
        startActivity(intent)
        finish()
    }
}
