package com.latihan.project_tugas_android_akhir.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.latihan.project_tugas_android_akhir.R
import com.latihan.project_tugas_android_akhir.adapters.StudentAdapter
import com.latihan.project_tugas_android_akhir.database.DatabaseHelper
import com.latihan.project_tugas_android_akhir.databinding.FragmentStudentsBinding
import com.latihan.project_tugas_android_akhir.models.Mahasiswa

class StudentsFragment : Fragment() {
    
    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var studentAdapter: StudentAdapter
    private var studentsList = mutableListOf<Mahasiswa>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        databaseHelper = DatabaseHelper(requireContext())
        
        setupRecyclerView()
        loadStudents()
        
        binding.fabAddStudent.setOnClickListener {
            showAddStudentDialog()
        }
    }
    
    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter(
            studentsList,
            onEditClick = { mahasiswa -> showEditStudentDialog(mahasiswa) },
            onDeleteClick = { mahasiswa -> showDeleteConfirmation(mahasiswa) }
        )
        
        binding.recyclerViewStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studentAdapter
        }
    }
    
    private fun loadStudents() {
        studentsList.clear()
        studentsList.addAll(databaseHelper.ambilSemuaData())
        studentAdapter.notifyDataSetChanged()
        
        binding.textViewEmpty.visibility = if (studentsList.isEmpty()) View.VISIBLE else View.GONE
    }
    
    private fun showAddStudentDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_student_form)
        
        val editNim = dialog.findViewById<TextInputEditText>(R.id.edit_text_nim)
        val editNama = dialog.findViewById<TextInputEditText>(R.id.edit_text_nama)
        val editJurusan = dialog.findViewById<TextInputEditText>(R.id.edit_text_jurusan)
        val btnSave = dialog.findViewById<View>(R.id.button_save)
        val btnCancel = dialog.findViewById<View>(R.id.button_cancel)
        
        btnSave.setOnClickListener {
            val nim = editNim.text.toString().trim()
            val nama = editNama.text.toString().trim()
            val jurusan = editJurusan.text.toString().trim()
            
            when {
                nim.isEmpty() || nama.isEmpty() || jurusan.isEmpty() -> {
                    Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
                }
                databaseHelper.isNimExists(nim) -> {
                    Toast.makeText(requireContext(), R.string.nim_exists, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val mahasiswa = Mahasiswa(nim = nim, nama = nama, jurusan = jurusan)
                    val result = databaseHelper.tambahData(mahasiswa)
                    
                    if (result > 0) {
                        Toast.makeText(requireContext(), R.string.student_added, Toast.LENGTH_SHORT).show()
                        loadStudents()
                        dialog.dismiss()
                    }
                }
            }
        }
        
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }
    
    private fun showEditStudentDialog(mahasiswa: Mahasiswa) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_student_form)
        
        val editNim = dialog.findViewById<TextInputEditText>(R.id.edit_text_nim)
        val editNama = dialog.findViewById<TextInputEditText>(R.id.edit_text_nama)
        val editJurusan = dialog.findViewById<TextInputEditText>(R.id.edit_text_jurusan)
        val btnSave = dialog.findViewById<View>(R.id.button_save)
        val btnCancel = dialog.findViewById<View>(R.id.button_cancel)
        
        // Pre-fill data
        editNim.setText(mahasiswa.nim)
        editNim.isEnabled = false // NIM tidak bisa diubah
        editNama.setText(mahasiswa.nama)
        editJurusan.setText(mahasiswa.jurusan)
        
        btnSave.setOnClickListener {
            val nama = editNama.text.toString().trim()
            val jurusan = editJurusan.text.toString().trim()
            
            if (nama.isEmpty() || jurusan.isEmpty()) {
                Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
            } else {
                val updatedMahasiswa = mahasiswa.copy(nama = nama, jurusan = jurusan)
                val result = databaseHelper.ubahData(updatedMahasiswa)
                
                if (result > 0) {
                    Toast.makeText(requireContext(), R.string.student_updated, Toast.LENGTH_SHORT).show()
                    loadStudents()
                    dialog.dismiss()
                }
            }
        }
        
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }
    
    private fun showDeleteConfirmation(mahasiswa: Mahasiswa) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_confirm)
            .setPositiveButton(R.string.delete) { _, _ ->
                val result = databaseHelper.hapusData(mahasiswa.nim)
                if (result > 0) {
                    Toast.makeText(requireContext(), R.string.student_deleted, Toast.LENGTH_SHORT).show()
                    loadStudents()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
