package com.example.vida.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vida.R
import com.example.vida.databinding.ActivityNotificationSettingsBinding
import com.example.vida.databinding.ContactDeleteSheetBinding
import com.example.vida.databinding.SpeedLimitNotificationSheetBinding
import com.example.vida.ui.model.Contact
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.google.android.material.bottomsheet.BottomSheetDialog

class NotificationSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.rlPanicAlert.setOnClickListener {
            val intent = Intent(this, PanicAlertActivity::class.java)
            startActivity(intent)
        }

        binding.scSpeedLimit.setOnClickListener {
//            binding.scSpeedLimit.isChecked=false
            showBottomSheetDialog()
        }

        /*binding.scSpeedLimit.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.scSpeedLimit.isChecked=false
            showBottomSheetDialog()
        }*/
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        val bottomBinding = SpeedLimitNotificationSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomBinding.root)

        bottomBinding.scSpeedLimitSheet.isChecked = true

        bottomBinding.tvBack.setOnClickListener {
            binding.scSpeedLimit.isChecked = false
            if (binding.scSpeedLimit.isChecked) {
                binding.ivSpeedEdit.show()
            } else {
                binding.ivSpeedEdit.hide()
            }
            bottomSheetDialog.dismiss()
        }
        bottomBinding.tvSet.setOnClickListener {
            binding.scSpeedLimit.isChecked = true
            if (binding.scSpeedLimit.isChecked) {
                binding.ivSpeedEdit.show()
            } else {
                binding.ivSpeedEdit.hide()
            }
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
//            binding.scSpeedLimit.isChecked = false
        }

        bottomSheetDialog.show()
    }
}