package com.example.vida.ui.register

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.marginTop
import com.example.vida.R
import com.example.vida.databinding.ActivityScooterDetailBinding
import com.example.vida.ui.dashboard.MyGarbageActivity
import com.example.vida.ui.personaldetail.PersonalDetailActivity
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.invisible
import com.example.vida.utils.extension.show

class ScooterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScooterDetailBinding
    private lateinit var mContext: AppCompatActivity
    private lateinit var value: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScooterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this
        getBundleData()
        initializeView()

    }

    private fun getBundleData() {
        value = intent.getStringExtra("fromFragment").toString()
    }

    private fun initializeView() {
        if (value == "scooterFragment") {
            binding.tvAddYourScooter.hide()
            binding.tvNotYourScooter.hide()
//            /*TODO*/binding.rlHeader.show()
            binding.tvConfirmScooter.hide()
            sendUserToActivity(MyGarbageActivity::class.java)
//            /*TODO*/binding.imgBackArrow.setOnClickListener { finish() }
        } else {
            binding.tvAddYourScooter.show()
            binding.tvNotYourScooter.show()
//            /*TODO*/binding.rlHeader.hide()
            binding.tvConfirmScooter.show()
            sendUserToActivity(PersonalDetailActivity::class.java)
        }
        binding.tvAddYourScooter.setOnClickListener {
            val intent = Intent(this@ScooterDetailActivity, AddScooterActivity::class.java)
            intent.putExtra("fromActivity", "ScooterDetail")
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        /*binding.btnContinue.setOnClickListener {
            val intent = Intent(this@ScooterDetailActivity, PersonalDetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }*/

    }

    private fun sendUserToActivity(activityClass: Class<out Activity?>) {
        binding.btnContinue.setOnClickListener {
            val intent = Intent(this@ScooterDetailActivity, activityClass)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

}