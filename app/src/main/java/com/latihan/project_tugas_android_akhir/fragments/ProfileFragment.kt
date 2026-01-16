package com.latihan.project_tugas_android_akhir.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.latihan.project_tugas_android_akhir.AboutActivity
import com.latihan.project_tugas_android_akhir.MainActivity
import com.latihan.project_tugas_android_akhir.R
import com.latihan.project_tugas_android_akhir.databinding.FragmentProfileBinding
import com.latihan.project_tugas_android_akhir.utils.PreferencesManager

class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesManager: PreferencesManager
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        preferencesManager = PreferencesManager.getInstance(requireContext())
        val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
        
        // Display user info from Firebase or Preferences as fallback
        val displayName = currentUser?.displayName ?: preferencesManager.getUsername()
        val email = currentUser?.email ?: ""
        
        binding.textViewUsername.text = if (email.isNotEmpty()) "$displayName\n($email)" else displayName
        
        // Set dark mode switch
        binding.switchDarkMode.isChecked = preferencesManager.isDarkMode()
        
        setupListeners()
    }
    
    private fun setupListeners() {
        // Dark Mode Switch
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            preferencesManager.saveDarkMode(isChecked)
            (activity as? MainActivity)?.applyDarkMode()
        }
        
        // About Button
        binding.buttonAbout.setOnClickListener {
            val intent = Intent(requireActivity(), AboutActivity::class.java)
            startActivity(intent)
        }
        
        // Logout Button
        binding.buttonLogout.setOnClickListener {
            (activity as? MainActivity)?.performLogout()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
