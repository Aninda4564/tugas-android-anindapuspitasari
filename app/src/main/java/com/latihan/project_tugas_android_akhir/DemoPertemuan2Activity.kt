package com.latihan.project_tugas_android_akhir

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.latihan.project_tugas_android_akhir.databinding.ActivityDemoPertemuan2Binding

class DemoPertemuan2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityDemoPertemuan2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoPertemuan2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Demo Pertemuan 2"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnAdd.setOnClickListener { calculate('+') }
        binding.btnSubtract.setOnClickListener { calculate('-') }
        binding.btnMultiply.setOnClickListener { calculate('*') }
        binding.btnDivide.setOnClickListener { calculate('/') }
    }

    private fun calculate(operation: Char) {
        val sNum1 = binding.etNum1.text.toString()
        val sNum2 = binding.etNum2.text.toString()

        if (sNum1.isEmpty() || sNum2.isEmpty()) {
            Toast.makeText(this, "Isi kedua angka!", Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = sNum1.toDouble()
        val num2 = sNum2.toDouble()
        var result = 0.0

        when (operation) {
            '+' -> result = num1 + num2
            '-' -> result = num1 - num2
            '*' -> result = num1 * num2
            '/' -> {
                if (num2 == 0.0) {
                    Toast.makeText(this, "Tidak bisa membagi dengan nol!", Toast.LENGTH_SHORT).show()
                    return
                }
                result = num1 / num2
            }
        }

        binding.tvResult.text = "Hasil: $result"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
