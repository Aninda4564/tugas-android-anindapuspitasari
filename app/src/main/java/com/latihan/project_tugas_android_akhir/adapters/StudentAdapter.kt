package com.latihan.project_tugas_android_akhir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latihan.project_tugas_android_akhir.databinding.ItemStudentBinding
import com.latihan.project_tugas_android_akhir.models.Mahasiswa

class StudentAdapter(
    private val students: List<Mahasiswa>,
    private val onEditClick: (Mahasiswa) -> Unit,
    private val onDeleteClick: (Mahasiswa) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    
    inner class StudentViewHolder(private val binding: ItemStudentBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(mahasiswa: Mahasiswa) {
            binding.textViewNim.text = "NIM: ${mahasiswa.nim}"
            binding.textViewNama.text = mahasiswa.nama
            binding.textViewJurusan.text = mahasiswa.jurusan
            
            binding.root.setOnClickListener {
                onEditClick(mahasiswa)
            }
            
            binding.root.setOnLongClickListener {
                onDeleteClick(mahasiswa)
                true
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }
    
    override fun getItemCount() = students.size
}
