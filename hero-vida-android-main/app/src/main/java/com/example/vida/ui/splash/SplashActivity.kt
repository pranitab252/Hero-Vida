package com.example.vida.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.vida.ui.dashboard.DashboardActivity
import androidx.lifecycle.lifecycleScope
import com.example.vida.network.WebService
import com.example.vida.ui.intro.OnBoardingActivity
import com.example.vida.ui.main.MainActivity
import com.example.vida.ui.otc.LoginOtcActivity
import com.example.vida.utils.extension.Constants
import com.example.vida.utils.extension.Utils
import com.example.vida.ui.personaldetail.PersonalDetailActivity
import com.example.vida.ui.register.PinActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    override fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // FullScreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        initializeView()

        testApiCalls()

    }

    private fun testApiCalls() {
        Utils.printDevLog(mssg = "uniqueId: ${WebService.getUniqueId()}")

        lifecycleScope.launchWhenCreated {

//            WebService.setAuthToken(this@SplashActivity, Constants.TEST_TOKEN)

//            Utils.testOtcRegister(this@SplashActivity)
//            Utils.testActive2(this@SplashActivity)
//            Utils.testOtcLoginApi2(this@SplashActivity)
//            Utils.testDashboard(this@SplashActivity)
//            Utils.callGet VehicleLocationApi(this@SplashActivity)
        }
//        Utils.testOtcLoginApi(this)
    }

    private fun initializeView() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            checkLoginAndHome()
        }, 300)
    }

    private fun checkLoginAndHome() {
        coroutineScope.launch {
            gotoOnBoarding()
        }
    }


    private fun gotoMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun gotoOnBoarding() {

        startActivity(
            Intent(
                this@SplashActivity,
                if (Constants.FLAG_ONBOARDING_FLOW) OnBoardingActivity::class.java else LoginOtcActivity::class.java
            )
        )
        finish()
    }
}
