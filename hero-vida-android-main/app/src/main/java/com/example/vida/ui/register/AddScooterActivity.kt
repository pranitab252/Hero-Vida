package com.example.vida.ui.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.vida.R
import com.example.vida.databinding.ActivityAddScooterBinding
import com.example.vida.utils.extension.Functions.afterTextChanged
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show

class AddScooterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddScooterBinding
    private lateinit var mContext: AppCompatActivity
    private lateinit var whichActivity: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScooterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext = this
        getBundle()
        initializeView()
    }

    private fun getBundle() {
        whichActivity = intent.getStringExtra("fromActivity").toString()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initializeView() {
        if (whichActivity == "ScooterDetail") {
            binding.llMain.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_main_bg, null)
            binding.rlHeader.hide()
            binding.tvAddVehicle.show()
            binding.tvIdentifyNumber.show()
            sendUserToActivity(ScooterDetailActivity::class.java, "addScooterActivity")
        } else {
            binding.llMain.setBackgroundColor(ResourcesCompat.getColor(resources,
                R.color.colorWhite,
                null))
            binding.imgBackArrow.setOnClickListener {
                finish()
            }
            binding.rlHeader.show()
            binding.tvAddVehicle.hide()
            binding.tvIdentifyNumber.hide()
            sendUserToActivity(OTPActivity::class.java, "scooterFragment")
        }
        binding.etVINNumber.afterTextChanged({
            if (it.isNotEmpty()) {
                binding.etVINNumber.background = mContext.resources.getDrawable(
                    R.drawable.edittext_button_green_shape, null
                )
                binding.etVINNumber.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(mContext, R.drawable.ic_tick_green), null
                )
            } else {
                binding.etVINNumber.background = mContext.resources.getDrawable(
                    R.drawable.edittext_button_gray_shape, null
                )
                binding.etVINNumber.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(mContext, R.drawable.ic_tick_gray), null
                )
            }
        }, {})
    }

    private fun sendUserToActivity(activityClass: Class<out Activity?>, value: String) {
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this@AddScooterActivity, activityClass)
            intent.putExtra("RegisterToOtp", value)
            startActivity(intent)
        }
    }
}