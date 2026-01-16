package com.latihan.project_tugas_android_akhir

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.latihan.project_tugas_android_akhir.databinding.ActivityMainBinding
import com.latihan.project_tugas_android_akhir.fragments.HomeFragment
import com.latihan.project_tugas_android_akhir.fragments.ProfileFragment
import com.latihan.project_tugas_android_akhir.fragments.StudentsFragment
import com.latihan.project_tugas_android_akhir.utils.PreferencesManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize PreferencesManager first
        preferencesManager = PreferencesManager.getInstance(this)
        
        // Apply dark mode BEFORE super.onCreate
        val isDarkMode = preferencesManager.isDarkMode()
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        
        // Setup bottom navigation
        setupBottomNavigation()
        
        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }
    
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_students -> {
                    loadFragment(StudentsFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    
    fun applyDarkMode() {
        val isDarkMode = preferencesManager.isDarkMode()
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    
    fun performLogout() {
        AlertDialog.Builder(this)
            .setTitle(R.string.logout)
            .setMessage(R.string.logout_confirm)
            .setPositiveButton(R.string.ok) { _, _ ->
                // Sign out from Firebase
                com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                
                // Clear local preferences
                preferencesManager.clearAll()
                
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
