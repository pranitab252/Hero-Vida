package com.example.vida.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.vida.R
import com.example.vida.databinding.ActivityLanding2Binding
import com.example.vida.ui.dashboard.DashboardActivity
import com.example.vida.ui.register.RegisterActivity
import com.example.vida.ui.register.ScooterDetailActivity
import com.example.vida.ui.register.SecurityPinActivity
import com.google.android.material.button.MaterialButton

class LandingActivity : AppCompatActivity() {
    private var binding: ActivityLanding2Binding? = null
    private lateinit var btnRegister: TextView
    private lateinit var mContext: AppCompatActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanding2Binding.inflate(layoutInflater)
        setContentView(binding!!.root)

        mContext = this
        initializeView()

    }

    private fun initializeView() {
        binding!!.btnRegister.setOnClickListener {
            setIntent("Register")
        }
        binding!!.btnLogin.setOnClickListener {
            setIntent("Login")
        }


        val run: Runnable = Runnable {
            binding!!.clLanding.background = resources.getDrawable(R.drawable.landing_screen_2)
        }
        binding!!.clLanding.postDelayed(run, 3000)
    }

    private fun setIntent(value: String) {
        val intent = Intent(this@LandingActivity, RegisterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("loginOrRegister", value)
        startActivity(intent)
    }

}