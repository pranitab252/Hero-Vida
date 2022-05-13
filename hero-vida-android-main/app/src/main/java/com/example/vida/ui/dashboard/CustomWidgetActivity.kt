package com.example.vida.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vida.databinding.ActivityCustomWidgetBinding

class CustomWidgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomWidgetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomWidgetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
    }
}