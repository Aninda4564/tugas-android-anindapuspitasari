package com.latihan.project_tugas_android_akhir.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.latihan.project_tugas_android_akhir.CalculatorActivity
import com.latihan.project_tugas_android_akhir.R
import com.latihan.project_tugas_android_akhir.TicketActivity
import com.latihan.project_tugas_android_akhir.api.RetrofitClient
import com.latihan.project_tugas_android_akhir.databinding.FragmentHomeBinding
import com.latihan.project_tugas_android_akhir.models.WeatherResponse
import com.latihan.project_tugas_android_akhir.utils.PreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesManager: PreferencesManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        preferencesManager = PreferencesManager.getInstance(requireContext())
        
        // Display welcome message
        val username = preferencesManager.getUsername()
        binding.textViewWelcome.text = getString(R.string.welcome) + ", $username!"
        
        // Load weather data
        loadWeatherData()
        
        // Setup button listeners
        setupButtons()
    }
    
    private fun loadWeatherData() {
        binding.progressBar.visibility = View.VISIBLE
        binding.cardWeather.visibility = View.GONE
        
        // Coordinates for Jakarta (example)
        val latitude = -6.2088
        val longitude = 106.8456
        
        RetrofitClient.weatherApiService.getCurrentWeather(latitude, longitude)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    binding.progressBar.visibility = View.GONE
                    
                    if (response.isSuccessful && response.body() != null) {
                        val weather = response.body()!!
                        binding.cardWeather.visibility = View.VISIBLE
                        binding.textViewTemperature.text = 
                            "${weather.currentWeather.temperature}°C"
                        binding.textViewWeatherDesc.text = 
                            getWeatherDescription(weather.currentWeather.weatherCode)
                    } else {
                        showError()
                    }
                }
                
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    showError()
                }
            })
    }
    
    private fun showError() {
        binding.cardWeather.visibility = View.VISIBLE
        binding.textViewTemperature.text = "--°C"
        binding.textViewWeatherDesc.text = getString(R.string.weather_error)
    }
    
    private fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Cerah"
            1, 2, 3 -> "Berawan"
            45, 48 -> "Berkabut"
            51, 53, 55 -> "Gerimis"
            61, 63, 65 -> "Hujan"
            71, 73, 75 -> "Salju"
            95 -> "Badai Petir"
            else -> "Tidak Diketahui"
        }
    }
    
    private fun setupButtons() {
        binding.buttonCalculator.setOnClickListener {
            startActivity(Intent(requireContext(), CalculatorActivity::class.java))
        }
        
        binding.buttonTicket.setOnClickListener {
            startActivity(Intent(requireContext(), TicketActivity::class.java))
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
