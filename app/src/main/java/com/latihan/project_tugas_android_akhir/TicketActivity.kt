package com.latihan.project_tugas_android_akhir

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.latihan.project_tugas_android_akhir.databinding.ActivityTicketBinding

class TicketActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityTicketBinding
    private val CHANNEL_ID = "ticket_channel"
    private val NOTIFICATION_ID = 1
    private val REQUEST_CODE_NOTIFICATION = 100
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.ticket_title)
        
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        
        createNotificationChannel()
        
        binding.buttonOrderTicket.setOnClickListener {
            processTicketOrder()
        }
    }
    
    private fun processTicketOrder() {
        val nama = binding.editTextTicketName.text.toString().trim()
        val jumlahStr = binding.editTextTicketQuantity.text.toString().trim()
        
        when {
            nama.isEmpty() || jumlahStr.isEmpty() -> {
                Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
            }
            else -> {
                val jumlah = jumlahStr.toIntOrNull() ?: 0
                if (jumlah <= 0) {
                    Toast.makeText(this, "Jumlah tiket harus lebih dari 0!", Toast.LENGTH_SHORT).show()
                    return
                }
                
                showLoadingDialog(nama, jumlah)
            }
        }
    }
    
    private fun showLoadingDialog(nama: String, jumlah: Int) {
        val dialog = AlertDialog.Builder(this)
            .setMessage(R.string.processing)
            .setCancelable(false)
            .create()
        
        dialog.show()
        
        // Simulate processing for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            showNotification(nama, jumlah)
        }, 2000)
    }
    
    private fun showNotification(nama: String, jumlah: Int) {
        // Check permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE_NOTIFICATION
                )
                return
            }
        }
        
        // Create intent for notification click
        val intent = Intent(this, TicketActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("NAMA", nama)
            putExtra("JUMLAH", jumlah)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.ticket_title))
            .setContentText(getString(R.string.ticket_success, nama))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification)
        
        // Show dialog immediately
        showTicketDetailDialog(nama, jumlah)
    }
    
    private fun showTicketDetailDialog(nama: String, jumlah: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.ticket_title)
            .setMessage(getString(R.string.ticket_detail, nama, jumlah.toString()))
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Ticket Notifications"
            val descriptionText = "Notifications for ticket bookings"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
