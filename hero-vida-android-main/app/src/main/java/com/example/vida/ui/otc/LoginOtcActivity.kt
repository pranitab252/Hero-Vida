package com.example.vida.ui.otc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vida.databinding.ActivityLoginOtcBinding
import com.example.vida.network.WebService
import com.example.vida.ui.dashboard.DashboardActivity
import com.example.vida.utils.extension.Constants
import com.example.vida.utils.extension.Utils
import com.otc.alice.api.model.LocationAndSecurity
import com.otc.alice.api.model.Shared
import com.otc.alice.api.model.Welcome
import com.otcengineering.otcble.core.utils.UniqueDeviceID


class LoginOtcActivity : AppCompatActivity() {
    private val LOG_TAG = Constants.TAG_DEV_LOG + this::class.java.simpleName
    private lateinit var binding: ActivityLoginOtcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginOtcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initLogic()
    }

    private fun init() {
        binding.edtPhone.setText(Constants.TEST_MOBILE_PHONE)
        binding.edtPass.setText(Constants.TEST_PASSWORD)
        binding.edtUdidImei.setText(UniqueDeviceID.getUniqueId())

        binding.swipeRefresh.isEnabled = false

        binding.btnLogin.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                testLoginApi(true)
            }
        }

        binding.btnRefreshToken.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                testRefreshToken(true)
            }
        }

        binding.btnGotoDashboard.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )
        }

        /*

        binding.btnGotoVehicleStatus.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToVehicleStatusFragment()
            findNavController().navigate(action)
        }

        binding.btnGotoBluetooth.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToBleFragment()
            findNavController().navigate(action)
        }

        binding.btnGotoActions.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToActionsFragment()
            findNavController().navigate(action)
        }

        binding.includedLogger.btnClearLogs.setOnClickListener {
            Utils.clearLogs(binding.includedLogger.txtLog)
        }

        binding.includedLogger.switchLogsVisibility.setOnCheckedChangeListener { _, isChecked ->
            binding.includedLogger.txtLog.isVisible = isChecked
            binding.includedLogger.btnClearLogs.isVisible = isChecked
        }

        binding.swipeLayout.setOnRefreshListener {
            lifecycleScope.launchWhenCreated {
                Utils.clearLogs(binding.includedLogger.txtLog)
                Utils.clearLogs(binding.includedProminentLogger.txtProminentLog)

                testLoginApi()

                binding.swipeLayout.isRefreshing = false
            }
        }*/

    }

    private fun initLogic() {
        lifecycleScope.launchWhenCreated {

            binding.swipeRefresh.isRefreshing = true
            if (WebService.getAuthToken(this@LoginOtcActivity).isNotEmpty()) {
                val isAuthorized =
                    callGetRemoteActionStatusApi(this@LoginOtcActivity, showRefresh = false)
                if (!isAuthorized)
                    testLoginApi(false)
                else
                    binding.btnGotoDashboard.isEnabled = true
            }
            binding.swipeRefresh.isRefreshing = false
        }

    }


    private suspend fun callGetRemoteActionStatusApi(ctx: Context, showRefresh: Boolean): Boolean {
        try {
            if (showRefresh) binding.swipeRefresh.isRefreshing = true

            val response = WebService.Location(ctx).getRemoteActionStatus()
            Utils.printDevLog(mssg = "response: $response")

            if (response.body() != null) {
                Utils.printDevLog(mssg = "response.body(): ${response.body()}")

                val remoteActionsStatus =
                    LocationAndSecurity.RemoteActionsStatus.parseFrom(response.body()?.data?.value)
                Utils.printDevLog(
                    mssg = "remoteActionsStatus: $remoteActionsStatus"
                )

                val allFields = remoteActionsStatus.allFields
                Utils.printDevLog(
                    mssg =
                    "remote status all Fields: $allFields"
                )

                if (response.body()?.statusValue == Shared.OTCStatus.SUCCESS_VALUE)
                    return true
            }


        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error:" + e.message)
        } finally {
            if (showRefresh) binding.swipeRefresh.isRefreshing = false
        }

        return false
    }


    private suspend fun testLoginApi(showRefresh: Boolean) {
        val welcome = Welcome.Login.newBuilder()

        welcome.phoneNumber = binding.edtPhone.text.toString()
        welcome.password = binding.edtPass.text.toString()
        welcome.mobileIMEI = binding.edtUdidImei.text.toString()

        try {
            if (showRefresh) binding.swipeRefresh.isRefreshing = true

            val response = WebService.Welcome(this).login(welcome.build())

            Utils.printDevLog(
                LOG_TAG,
                "$response"/*, binding.includedLogger.txtLog*/
            )
            if (response.body() != null) {

                Utils.printDevLog(
                    LOG_TAG,
                    "${response.body()}"/*, binding.includedLogger.txtLog*/
                )

                if (response.body()?.status == Shared.OTCStatus.NEW_MOBILE)
                    testChangePhoneApi(reLogin = true, showRefresh)
                else {

                    val loginResponse =
                        Welcome.LoginResponse.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(
                        LOG_TAG,
                        "$loginResponse"/*,
                            binding.includedLogger.txtLog*/
                    )


//                            if (response.body()?.statusValue == 81)
//                                testChangePhoneApi(reLogin = true)
//                            else {

                    response.body()?.status.let {
                        /*Utils.printProminentScreenLog(
                            "$it",
                            binding.includedPromentLogger.txtProminentLog
                        )*/

                        Toast.makeText(
                            this,
                            "$it",
                            Toast.LENGTH_SHORT
                        ).show()


                        binding.btnGotoDashboard.isEnabled =
                            response.body()?.status == Shared.OTCStatus.SUCCESS
                    }

                    /*Utils.printProminentScreenLog(
                        "userId: ${loginResponse.userId}",
                        binding.includedProminentLogger.txtProminentLog
                    )*/


                    WebService.setAuthToken(
                        this,
                        loginResponse.apiToken
                    )
//                            }

                }
            }

        } catch (e: Exception) {
            Utils.printDevLog(LOG_TAG, "Error: " + e.message/*, binding.includedLogger.txtLog*/)

            /*Utils.printProminentScreenLog(
                "Error:please check", binding.includedProminentLogger.txtProminentLog
            )*/
        } finally {
            if (showRefresh) binding.swipeRefresh.isRefreshing = false
        }
    }


    private suspend fun testChangePhoneApi(reLogin: Boolean, showRefresh: Boolean) {
        try {

            val welcome = Welcome.ChangePhone.newBuilder()

            welcome.phoneNumber = binding.edtPhone.text.toString()
            welcome.password = binding.edtPass.text.toString()
            welcome.mobileIMEI = binding.edtUdidImei.text.toString()

            if (showRefresh) binding.swipeRefresh.isRefreshing = true
            val response = WebService.Welcome(this).changeMobile(welcome.build())

            Utils.printDevLog(LOG_TAG, "$response"/*, binding.includedLogger.txtLog*/)

            if (response.body() != null) {
                Utils.printDevLog(
                    LOG_TAG,
                    "${response.body()}"/*, binding.includedLogger.txtLog*/
                )

                if (response.body()?.status == Shared.OTCStatus.SUCCESS && reLogin) {
                    testLoginApi(showRefresh)
                }
            }


        } catch (e: Exception) {
            Utils.printDevLog(LOG_TAG, "Error: " + e.message/*, binding.includedLogger.txtLog*/)

            /*Utils.printProminentScreenLog(
                "Error:please check", binding.includedProminentLogger.txtProminentLog
            )*/
        } finally {
            if (showRefresh) binding.swipeRefresh.isRefreshing = false
        }

    }


    private suspend fun testRefreshToken(showRefresh: Boolean) {
        try {
            if (showRefresh) binding.swipeRefresh.isRefreshing = true
            val response = WebService.Welcome(this).refreshToken()

            Utils.printDevLog(
                LOG_TAG,
                "$response"/*, binding.includedLogger.txtLog*/
            )

            if (response.body() != null) {
                Utils.printDevLog(
                    LOG_TAG,
                    "${response.body()}"/*, binding.includedLogger.txtLog*/
                )



                response.body()?.status.let {
                    /*Utils.printProminentScreenLog(
                        "$it",
                        binding.includedProminentLogger.txtProminentLog
                    )*/

                    Toast.makeText(
                        this,
                        "$it",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                if (response.body()?.statusValue == 1) {
                    val loginResponse =
                        Welcome.LoginResponse.parseFrom(response.body()?.data?.value)

                    Utils.printDevLog(
                        LOG_TAG,
                        "$loginResponse"/*,
                            binding.includedLogger.txtLog*/
                    )

                    WebService.setAuthToken(
                        this,
                        loginResponse.apiToken
                    )
                }

            }

        } catch (e: Exception) {
            Utils.printDevLog(LOG_TAG, "Error: " + e.message/*, binding.includedLogger.txtLog*/)

            /*Utils.printProminentScreenLog(
                "Error:please check", binding.includedProminentLogger.txtProminentLog
            )*/
        } finally {
            if (showRefresh) binding.swipeRefresh.isRefreshing = false
        }
    }
}