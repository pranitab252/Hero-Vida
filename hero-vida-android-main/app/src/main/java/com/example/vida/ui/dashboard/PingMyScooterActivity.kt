package com.example.vida.ui.dashboard

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.vida.R
import com.example.vida.databinding.ActivityPingMyScooterBinding
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show


class PingMyScooterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPingMyScooterBinding
    private var countDownTimer: CountDownTimer? = null

    private var status = 0
    private val startTime = (10 * 1000).toLong()
    private val interval = (1 * 1000).toLong()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPingMyScooterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        initTimer()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.rlVolume.setOnClickListener {
            binding.rlSun.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.ivSun.setColorFilter(
                ContextCompat.getColor(this, R.color.colorNero),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            binding.rlVolume.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.ivVolume.setColorFilter(
                ContextCompat.getColor(this, R.color.colorNero),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            binding.tvStartPing.backgroundTintList =
                ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorMessageDisable,
                        null
                    )
                )
            binding.tvStartPing.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.colorUnSelectSeek,
                    null
                )
            )
            binding.tvStartPing.isEnabled = false
        }
    }

    private fun initTimer() {
        binding.tvStartPing.setOnClickListener {
            if (status == 0) {
                binding.tvFindYourScooter.hide()
                binding.dpPing.show()
                binding.lAnimationPing.show()
                (countDownTimer as CountDownTimer).start()
                status = 1
                binding.tvStartPing.text = "Stop"
                binding.tvStartPing.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorWhite,
                        null
                    )
                )
                binding.tvStartPing.backgroundTintList =
                    ColorStateList.valueOf(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.colorNero,
                            null
                        )
                    )
            } else if (status == 1) {
                (countDownTimer as CountDownTimer).cancel()
                binding.tvFindYourScooter.show()
                binding.dpPing.hide()
                binding.lAnimationPing.hide()
                binding.dpPing.setProgress(10)
                status = 0
                binding.tvStartPing.text = "Start"
                binding.tvStartPing.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorNero,
                        null
                    )
                )
                binding.tvStartPing.backgroundTintList =
                    ColorStateList.valueOf(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.colorRegisterButtonPrimary,
                            null
                        )
                    )
            } else {
                binding.dpPing.setProgress(10)
                binding.tvFindYourScooter.show()
                binding.dpPing.hide()
                binding.lAnimationPing.hide()
                status = 0
                binding.tvStartPing.text = "Start"
                binding.tvStartPing.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorNero,
                        null
                    )
                )
                binding.tvStartPing.backgroundTintList =
                    ColorStateList.valueOf(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.colorRegisterButtonPrimary,
                            null
                        )
                    )
            }
        }

        countDownTimer = object : CountDownTimer(startTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                binding.dpPing.setProgress(millisUntilFinished.toInt() / 1000)
            }

            override fun onFinish() {
                binding.dpPing.setProgress(0)
                binding.tvFindYourScooter.show()
                binding.dpPing.hide()
                binding.lAnimationPing.hide()
                binding.tvStartPing.text = "Start"
                binding.tvStartPing.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorNero,
                        null
                    )
                )
                binding.tvStartPing.backgroundTintList =
                    ColorStateList.valueOf(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.colorRegisterButtonPrimary,
                            null
                        )
                    )
                status = 2
            }
        }
    }
}