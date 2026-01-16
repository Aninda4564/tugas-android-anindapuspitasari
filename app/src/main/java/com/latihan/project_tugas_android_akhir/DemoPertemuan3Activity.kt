package com.latihan.project_tugas_android_akhir

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.latihan.project_tugas_android_akhir.databinding.ActivityDemoPertemuan3Binding
import kotlin.random.Random

class DemoPertemuan3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityDemoPertemuan3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoPertemuan3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Demo Pertemuan 3"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnToast.setOnClickListener {
            Toast.makeText(this, "Halo! Ini adalah fungsi Toast.", Toast.LENGTH_SHORT).show()
        }

        binding.btnChangeColor.setOnClickListener {
            val randomColor = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            binding.containerDemo3.setBackgroundColor(randomColor)
        }

        binding.btnExitDemo.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
