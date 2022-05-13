package com.example.vida.ui.quickcontrol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vida.R
import com.example.vida.databinding.ActivityRemoteImmobilizationConfirmBinding
import com.example.vida.databinding.LayoutHeaderBinding
import com.example.vida.network.WebService
import com.example.vida.ui.dashboard.DashboardActivity
import com.example.vida.ui.register.ScooterDetailActivity
import com.example.vida.utils.extension.Functions.afterTextChanged
import com.example.vida.utils.extension.Utils
import com.example.vida.utils.extension.showToast
import com.otc.alice.api.model.LocationAndSecurity
import com.otc.alice.api.model.Shared
import java.util.*
import kotlin.concurrent.timerTask

class RemoteImmobilizationConfirmActivity : AppCompatActivity() {
    private var isImmobilized: Boolean = false
    private lateinit var binding: ActivityRemoteImmobilizationConfirmBinding
    private lateinit var headerBinding: LayoutHeaderBinding

    private var timerForL1Alerts: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemoteImmobilizationConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        headerBinding = binding.icImmobilization

        getBundleData()
        initView()
        passingFocusOfEditTextToNext()

        initL1Alerts()
    }

    private fun getBundleData() {
        intent.getBooleanExtra("isImmobilized", false).let { isImmobilized = it }
    }

    private fun initView() {
        if (isImmobilized) {
            headerBinding.tvTitle.text = resources.getString(R.string.cancel_immobilization)
        } else {
            headerBinding.tvTitle.text = resources.getString(R.string.remote_immobilization)
        }
        headerBinding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.tvVerifyRemote.setOnClickListener {
            if (binding.etFirstDigitRemote.text.toString()
                    .isNotEmpty() && binding.etSecondDigitRemote.text.toString()
                    .isNotEmpty()
                && binding.etThirdDigitRemote.text.toString()
                    .isNotEmpty() && binding.etFourthDigitRemote.text.toString()
                    .isNotEmpty()
            ) {
                if (binding.etFirstDigitRemote.text.toString()
                        .equals("1") && binding.etSecondDigitRemote.text.toString()
                        .equals("2")
                    && binding.etThirdDigitRemote.text.toString()
                        .equals("3") && binding.etFourthDigitRemote.text.toString()
                        .equals("4")
                ) {
                    //Toast.makeText(this, "Successs", Toast.LENGTH_LONG).show()

                    /*val intent = Intent(
                        this@RemoteImmobilizationConfirmActivity,
                        DashboardActivity::class.java
                    )
                    intent.putExtra("remoteMessage", isImmobilized)
                    intent.putExtra("isImmobilized", isImmobilized)
                    startActivity(intent)*/

                    callPutImmobilizationApi(this, isImmobilized, true)

                } else {
                    binding.tvVerifyRemote.isEnabled = false
                    binding.tvVerifyRemote.background =
                        resources.getDrawable(R.drawable.button_unselect_bg)
                    binding!!.tvVerifyRemote.setTextColor(resources.getColor(R.color.colorWhite))
                    binding.tvRemoteDetail.setTextColor(resources.getColor(R.color.colorRed))
                    binding.tvRemotePinError.visibility = View.VISIBLE
                    binding.etFirstDigitRemote.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etSecondDigitRemote.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etThirdDigitRemote.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etFourthDigitRemote.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            when (this.currentFocus!!.id) {
                binding.etFourthDigitRemote.id -> {
                    binding.etThirdDigitRemote.requestFocus()
                    binding.etThirdDigitRemote.setText("")
                }
                binding.etThirdDigitRemote.id -> {
                    binding.etSecondDigitRemote.requestFocus()
                    binding.etSecondDigitRemote.setText("")
                }
                binding.etSecondDigitRemote.id -> {
                    binding.etFirstDigitRemote.requestFocus()
                    binding.etFirstDigitRemote.setText("")
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
        }
        return true
    }

    private fun passingFocusOfEditTextToNext() {
        binding.etFirstDigitRemote.afterTextChanged({
            if (binding.etFirstDigitRemote.isFocused) {
                if (binding.etFirstDigitRemote.text.toString().length == 1) //size as per your requirement
                {
                    binding.etSecondDigitRemote.requestFocus()
                }
            }
        }, {})
        binding.etSecondDigitRemote.afterTextChanged({
            if (binding.etSecondDigitRemote.isFocused) {
                if (binding.etSecondDigitRemote.text.toString().length == 1) //size as per your requirement
                {
                    binding.etThirdDigitRemote.requestFocus()
                }
            }
        }, {})
        binding.etThirdDigitRemote.afterTextChanged({
            if (binding.etThirdDigitRemote.isFocused) {
                if (binding.etThirdDigitRemote.text.toString().length == 1) //size as per your requirement
                {
                    binding.etFourthDigitRemote.requestFocus()
                }
            }
        }, {})
        binding.etFourthDigitRemote.afterTextChanged({
            if (binding.etFirstDigitRemote.text.toString()
                    .isNotEmpty() && binding.etSecondDigitRemote.text.toString()
                    .isNotEmpty()
                && binding.etThirdDigitRemote.text.toString()
                    .isNotEmpty() && binding.etFourthDigitRemote.text.toString()
                    .isNotEmpty()
            ) {
                if (binding.etFirstDigitRemote.text.toString()
                        .equals("1") && binding.etSecondDigitRemote.text.toString()
                        .equals("2")
                    && binding.etThirdDigitRemote.text.toString()
                        .equals("3") && binding.etFourthDigitRemote.text.toString()
                        .equals("4")
                ) {
                    //Toast.makeText(this, "Successs", Toast.LENGTH_LONG).show()

                    /*val intent = Intent(
                        this@RemoteImmobilizationConfirmActivity,
                        DashboardActivity::class.java
                    )
                    intent.putExtra("remoteMessage", isImmobilized)
                    intent.putExtra("isImmobilized", isImmobilized)
                    startActivity(intent)*/

                    callPutImmobilizationApi(this, isImmobilized, true)

                } else {
                    binding.tvRemotePinError.visibility = View.VISIBLE
                    binding.etFirstDigitRemote.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etSecondDigitRemote.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etThirdDigitRemote.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etFourthDigitRemote.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                }
            }
        }, {
            binding.tvRemoteDetail.setTextColor(resources.getColor(R.color.color4DigitPin))
            binding.tvRemotePinError.visibility = View.GONE
            binding.tvVerifyRemote.isEnabled = true
            binding.tvVerifyRemote.background = resources.getDrawable(R.drawable.ic_button_bg)
            binding!!.tvVerifyRemote.setTextColor(resources.getColor(R.color.colorNero))
            binding.etFirstDigitRemote.background =
                resources.getDrawable(R.drawable.phone_code_state)
            binding.etSecondDigitRemote.background =
                resources.getDrawable(R.drawable.phone_code_state)
            binding.etThirdDigitRemote.background =
                resources.getDrawable(R.drawable.phone_code_state)
            binding.etFourthDigitRemote.background =
                resources.getDrawable(R.drawable.phone_code_state)

        })
    }


    private fun callPutImmobilizationApi(
        ctx: Context,
        immobilize: Boolean,
        showRefresh: Boolean
    ) {
        lifecycleScope.launchWhenCreated {
            try {
                if (showRefresh) binding.swipeRefresh.isRefreshing = true

                val welcome = LocationAndSecurity.ImmobilizationAction.newBuilder()
                welcome.immobilize = immobilize

                val response = WebService.Location(ctx).putImmobilization(welcome.build())
                Utils.printDevLog(mssg = "response: $response")

                if (response.body() != null) {
                    Utils.printDevLog(mssg = "response.body(): ${response.body()}")

                    val immobilizationAction =
                        LocationAndSecurity.ImmobilizationAction.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(
                        mssg = "immobilizationAction: $immobilizationAction"
                    )


                    val allFields = immobilizationAction.allFields
                    Utils.printDevLog(
                        mssg =
                        "immobilizationAction all Fields: $allFields"
                    )


                    if (response.body()?.status == Shared.OTCStatus.DONGLE_NOT_CONNECTED) {
                        showToast("Dongle Not Connected", "Please check dongle", "", false)
                        goBackToRemoteImmobilization()
                    } else
                        immobilizationOrCancelInitiated()

                }


            } catch (e: Exception) {
                Utils.printDevLog(mssg = "Error:" + e.message)

            } finally {
                if (showRefresh) binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun immobilizationOrCancelInitiated() {
        val intent = Intent(
            this@RemoteImmobilizationConfirmActivity,
            DashboardActivity::class.java
        )
        intent.putExtra("remoteMessage", isImmobilized)
        intent.putExtra("isImmobilized", isImmobilized)
        startActivity(intent)
    }

    private fun goBackToRemoteImmobilization() {
        finish()
    }


    private fun initL1Alerts() {
        timerForL1Alerts = Timer()

        timerForL1Alerts?.scheduleAtFixedRate(timerTask {
            lifecycleScope.launchWhenCreated {
                Utils.callGetLatestAlertsNotificationListApi(
                    this@RemoteImmobilizationConfirmActivity,
                    1
                )
                Utils.printDevLog(mssg = "scheduleAtFixedRate called")
            }
        }, 1000L * 5, 1000L * 60) //5 secs, 1 minute

    }

    override fun onStop() {
        super.onStop()
        timerForL1Alerts?.cancel()
    }

}