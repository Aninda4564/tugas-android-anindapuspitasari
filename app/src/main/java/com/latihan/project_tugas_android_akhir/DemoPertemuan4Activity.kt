package com.latihan.project_tugas_android_akhir

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.latihan.project_tugas_android_akhir.databinding.ActivityDemoPertemuan4Binding

class DemoPertemuan4Activity : AppCompatActivity() {

    private lateinit var binding: ActivityDemoPertemuan4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoPertemuan4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Demo Pertemuan 4"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmitDemo.setOnClickListener {
            val name = binding.etDemoName.text.toString()
            val email = binding.etDemoEmail.text.toString()
            val address = binding.etDemoAddress.text.toString()

            if (name.isEmpty() || email.isEmpty() || address.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Mohon lengkapi semua data form!")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Data Terkirim")
                .setMessage("Nama: $name\nEmail: $email\nAlamat: $address")
                .setPositiveButton("Bagus", null)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
