package com.latihan.project_tugas_android_akhir

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.latihan.project_tugas_android_akhir.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCalculatorBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.calculator_title)
        
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        
        binding.buttonHitungLuas.setOnClickListener {
            hitungLuas()
        }
    }
    
    private fun hitungLuas() {
        val panjangStr = binding.editTextPanjangPersegi.text.toString().trim()
        val lebarStr = binding.editTextLebarPersegi.text.toString().trim()
        
        when {
            panjangStr.isEmpty() || lebarStr.isEmpty() -> {
                Toast.makeText(this, R.string.input_empty, Toast.LENGTH_SHORT).show()
            }
            else -> {
                try {
                    val panjang = panjangStr.toDouble()
                    val lebar = lebarStr.toDouble()
                    val luas = panjang * lebar
                    
                    binding.textViewHasilLuas.text = getString(R.string.luas_result, luas.toString())
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Input tidak valid!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
