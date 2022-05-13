package com.example.vida.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vida.R
import com.example.vida.databinding.ActivityDashboardBinding
import com.example.vida.utils.extension.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.concurrent.timerTask

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private var timerForL1Alerts: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Functions.setCustomizeStatusBar(
             window,
             ContextCompat.getColor(this, R.color.colorAccent), true
         )*/

        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null
        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)

        val toastMessage: Boolean = intent.getBooleanExtra("remoteMessage", true)
        val isImmobilized: Boolean = intent.getBooleanExtra("isImmobilized", false)
        val isPinSet: Boolean = intent.getBooleanExtra("setPinSuccess", false)

        // Create the Bundle to pass, you can put String, Integer, or serializable object
        val bundle = Bundle()
        bundle.putBoolean("remoteMessage", toastMessage)
        bundle.putBoolean("isImmobilized", isImmobilized)
        bundle.putBoolean("setPinSuccess", isPinSet)
        //bundle.putSerializable("USER", user) // Serializable Object
        navController.navigate(R.id.navigation_home, bundle)

        navView.setupWithNavController(navController)


    }

    override fun onStart() {
        super.onStart()
        initL1Alerts()
    }

    private fun initL1Alerts() {
        timerForL1Alerts = Timer()

        timerForL1Alerts?.scheduleAtFixedRate(timerTask {
            lifecycleScope.launchWhenCreated {
                Utils.callGetLatestAlertsNotificationListApi(this@DashboardActivity, 1)
                Utils.printDevLog(mssg = "scheduleAtFixedRate called")
            }
        }, 1000L * 5, 1000L * 10) //5 secs, 1 minute

    }

    override fun onStop() {
        super.onStop()
        timerForL1Alerts?.cancel()
    }


}