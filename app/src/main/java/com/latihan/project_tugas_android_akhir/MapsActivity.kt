package com.latihan.project_tugas_android_akhir

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.latihan.project_tugas_android_akhir.databinding.ActivityMapsBinding
import java.io.IOException
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set default location (e.g., Universitas Mercu Buana or Jakarta)
        val defaultLocation = LatLng(-6.2146, 106.8451) // Jakarta Coordinates
        mMap.addMarker(MarkerOptions().position(defaultLocation).title("Marker in Jakarta"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))

        // Enable UI control
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        // 1. Get User Location
        getMyLocation()

        // 2. Set Map Click Listener
        setMapOnClick()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapOnClick() {
        mMap.setOnMapClickListener { latLng ->
            // Clear previous markers
            mMap.clear()
            
            // Add new marker
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("New Location")
            )
            
            // Animate camera
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))

            // Get Address Name (Reverse Geocoding)
            getAddressName(latLng)
        }
    }

    private fun getAddressName(latLng: LatLng) {
        binding.textLocationAddress.text = getString(R.string.getting_address)
        
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            @Suppress("DEPRECATION")
            val listAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            
            if (!listAddresses.isNullOrEmpty()) {
                val address = listAddresses[0].getAddressLine(0)
                binding.textLocationAddress.text = address
            } else {
                binding.textLocationAddress.text = "Alamat tidak ditemukan"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            binding.textLocationAddress.text = "Gagal mengambil alamat (Koneksi Error)"
        }
    }
}
