package com.latihan.project_tugas_android_akhir.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.latihan.project_tugas_android_akhir.R

data class MateriSlide(
    val title: String,
    val description: String,
    val imageRes: Int
)

class MateriPagerAdapter(
    private val slides: List<MateriSlide>,
    private val onDemoClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MateriPagerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIllustration: ImageView = view.findViewById(R.id.iv_materi_illustration)
        val tvTitle: TextView = view.findViewById(R.id.tv_materi_title)
        val tvDescription: TextView = view.findViewById(R.id.tv_materi_description)
        val btnOpenDemo: View = view.findViewById(R.id.btn_open_demo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_materi_slide, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val slide = slides[position]
        holder.tvTitle.text = slide.title
        holder.tvDescription.text = slide.description
        holder.ivIllustration.setImageResource(slide.imageRes)

        holder.btnOpenDemo.setOnClickListener {
            onDemoClick(position)
        }
    }

    override fun getItemCount(): Int = slides.size
}
