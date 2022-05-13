package com.example.vida.ui.quickcontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vida.R
import com.example.vida.databinding.ActivityExitNavigationBinding
import com.example.vida.ui.dashboard.DashboardActivity

class ExitNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExitNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExitNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.llExitNavigation.setOnClickListener {
            startActivity(Intent(this@ExitNavigationActivity, DashboardActivity::class.java))
        }
    }
}