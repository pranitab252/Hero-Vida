package com.example.vida.ui.dashboard

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vida.R
import com.example.vida.databinding.ActivityDiagnosisProgressBinding
import com.example.vida.utils.extension.show


class DiagnosisProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnosisProgressBinding
    private val interval = (1 * 1000).toLong()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosisProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        animateDiagnosisProgress()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun animateDiagnosisProgress() {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                countDownProgress(11500, 100)
            }

            override fun onAnimationEnd(animation: Animation) {
                val intent =
                    Intent(this@DiagnosisProgressActivity, DiagnosticReportActivity::class.java)
                startActivity(intent)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        binding.vDiagnosisScan.show()
        binding.vDiagnosisGradient.show()
        binding.vDiagnosisScan.startAnimation(animation)
        binding.vDiagnosisGradient.startAnimation(animation)
    }

    private fun countDownProgress(startTime: Long, interval: Long) {
        object : CountDownTimer(startTime, interval) {
            var time = 0
            override fun onTick(millisUntilFinished: Long) {
//                binding.tvPercentAge.setText("${millisUntilFinished / 100}%")
                time = time + 1
                binding.tvPercentAge.text = "$time%"
            }

            override fun onFinish() {
            }
        }.start()
    }
}