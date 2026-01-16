package com.latihan.project_tugas_android_akhir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.latihan.project_tugas_android_akhir.adapters.MateriPagerAdapter
import com.latihan.project_tugas_android_akhir.adapters.MateriSlide
import com.latihan.project_tugas_android_akhir.databinding.ActivityMateriBinding

class MateriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMateriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slides = listOf(
            MateriSlide(
                getString(R.string.materi_pertemuan_1),
                getString(R.string.materi_desc_1),
                R.drawable.logo_aninda_app
            ),
            MateriSlide(
                getString(R.string.materi_pertemuan_2),
                getString(R.string.materi_desc_2),
                R.drawable.logo_aninda_app
            ),
            MateriSlide(
                getString(R.string.materi_pertemuan_3),
                getString(R.string.materi_desc_3),
                R.drawable.logo_aninda_app
            ),
            MateriSlide(
                getString(R.string.materi_pertemuan_4),
                getString(R.string.materi_desc_4),
                R.drawable.logo_aninda_app
            )
        )

        val adapter = MateriPagerAdapter(slides) { position ->
            val intent = when (position) {
                0 -> Intent(this, DemoPertemuan1Activity::class.java)
                1 -> Intent(this, DemoPertemuan2Activity::class.java)
                2 -> Intent(this, DemoPertemuan3Activity::class.java)
                3 -> Intent(this, DemoPertemuan4Activity::class.java)
                else -> null
            }
            intent?.let { startActivity(it) }
        }
        binding.viewPagerMateri.adapter = adapter

        // Sync TabLayout with ViewPager2
        TabLayoutMediator(binding.tabLayoutIndicator, binding.viewPagerMateri) { _, _ -> }.attach()

        binding.btnMateriAction.setOnClickListener {
            finish()
        }
    }
}
