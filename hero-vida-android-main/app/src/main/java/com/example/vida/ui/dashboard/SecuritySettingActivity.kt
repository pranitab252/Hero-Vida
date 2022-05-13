package com.example.vida.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vida.databinding.ActivitySecuritySettingBinding
import com.example.vida.utils.extension.showToastSuccess

class SecuritySettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecuritySettingBinding
    private var isPinChangeSuccess: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecuritySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundleData()
        initializeView()
    }

    private fun getBundleData() {
        intent.getBooleanExtra("changePinSuccess", false).let {
            if (it != null) {
                isPinChangeSuccess = it
            }
        }
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.rlChangePin.setOnClickListener {
            val intent = Intent(this, ChangeSecurityPinActivity::class.java)
            startActivity(intent)
        }
        if (isPinChangeSuccess) {
            showToastSuccess("PIN Changed successfully!", "", true)
        }
    }
}