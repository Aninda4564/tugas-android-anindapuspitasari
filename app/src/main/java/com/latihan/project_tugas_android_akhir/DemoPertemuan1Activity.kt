package com.latihan.project_tugas_android_akhir

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DemoPertemuan1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_pertemuan1)
        
        supportActionBar?.title = "Demo Pertemuan 1"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
