package com.example.vida.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.vida.databinding.ActivityL1AlertBinding
import com.example.vida.utils.extension.Constants
import com.example.vida.utils.extension.Utils
import java.util.*
import kotlin.concurrent.timerTask

class L1AlertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityL1AlertBinding
    private var isCriticalAlert = false
    private var timerForL1Alerts: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityL1AlertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        binding.ivCross.setOnClickListener {
            finish()
        }

        initViewForL1AlertType()

    //TODO: listen here but should not go in recursion
    //        initL1Alerts()
    }

    private fun initViewForL1AlertType() {
        intent?.getStringExtra(Constants.EXTRA_DATA)?.let {
            binding.tvTitle.text = it
            binding.tvTitleSmall.text = it
        }


        isCriticalAlert = intent?.getBooleanExtra(Constants.EXTRA_BOOLEAN, true) == true
        binding.rlAlertSmall.isVisible = isCriticalAlert


        //TODO: also set bkg img as per alert

    }


    private fun initL1Alerts() {
        timerForL1Alerts = Timer()

        var bool = false
        timerForL1Alerts?.scheduleAtFixedRate(timerTask {
            lifecycleScope.launchWhenCreated {
                bool = Utils.callGetLatestAlertsNotificationListApi(this@L1AlertActivity, 1)
                Utils.printDevLog(mssg = "scheduleAtFixedRate called")
            }
        }, 1000L * 5, 1000L * 60) //5 secs, 1 minute

        if (bool)
            finish()

    }

    override fun onStop() {
        super.onStop()
        timerForL1Alerts?.cancel()
    }
}