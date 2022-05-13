package com.example.vida.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.example.vida.R
import com.example.vida.databinding.ActivityOtpactivityBinding
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    private var value: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        passingFocusOfEditTextToNext()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {
        value = intent.getStringExtra("RegisterToOtp").toString()
        when (value) {
            "register" -> {
//                /*TODO*/binding.llMain.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_main_bg, null)
//                /*TODO*/binding.rlHeader.hide()
                binding.tvVerifyOTP.show()
                binding.imgEditNumber.show()
                binding.imgLogoBlackOTP.show()
                binding.llNewRegisterNow.hide()
                binding.imgVidaBlackBottom.hide()
                binding.llHavingTrouble.hide()
            }
            "Login" -> {
//                /*TODO*/binding.llMain.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_main_bg, null)
//                /*TODO*/binding.rlHeader.hide()
                binding.tvVerifyOTP.show()
                binding.imgEditNumber.show()
                binding.imgLogoBlackOTP.show()
                binding.llNewRegisterNow.hide()
                binding.imgVidaBlackBottom.hide()
                binding.llHavingTrouble.hide()
            }
            "ForgotPin" -> {
//                /*TODO*/binding.llMain.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_main_bg, null)
//                /*TODO*/binding.rlHeader.hide()
                binding.tvVerifyOTP.show()
                binding.imgEditNumber.show()
                binding.imgLogoBlackOTP.hide()
                binding.llNewRegisterNow.show()
                binding.imgVidaBlackBottom.show()
                binding.llHavingTrouble.show()
            }
            "scooterFragment" -> {
                /*TODO*//*binding.llMain.setBackgroundColor(ResourcesCompat.getColor(resources,
                    R.color.colorWhite,
                    null))*/
                //                /*TODO*/binding.rlHeader.show()
                binding.imgLogoBlackOTP.hide()
                binding.tvVerifyOTP.hide()
                binding.imgEditNumber.hide()
                /*TODO*//*binding.imgBackArrow.setOnClickListener {
                    finish()
                }*/
            }
        }

        binding.imgEditNumber.setOnClickListener {
            finish()
        }

        binding.tvVerify.setOnClickListener {
            if (binding.etFirstDigit.text.toString()
                    .isNotEmpty() && binding.etSecondDigit.text.toString()
                    .isNotEmpty()
                && binding.etThirdDigit.text.toString()
                    .isNotEmpty() && binding.etFourthDigit.text.toString()
                    .isNotEmpty()
            ) {
                if (binding.etFirstDigit.text.toString()
                        .equals("1") && binding.etSecondDigit.text.toString()
                        .equals("2")
                    && binding.etThirdDigit.text.toString()
                        .equals("3") && binding.etFourthDigit.text.toString()
                        .equals("4")
                ) {
                    //Toast.makeText(this, "Successs", Toast.LENGTH_LONG).show()

                    if (value == "Login") {
                        setIntent(value)
                    } else if (value == "ForgotPin") {
                        setIntent(value)
                    } else {
                        val intent = Intent(this@OTPActivity, ScooterDetailActivity::class.java)
                        startActivity(intent)
                    }
                    binding.tvOtpInvalid.visibility = View.GONE
                    binding.tvResendOTP.visibility = View.GONE
                    binding.tvResendOTPText.visibility = View.GONE
                    binding.tvRemainingTime.visibility = View.GONE
                } else {
                    binding.tvOtpInvalid.visibility = View.VISIBLE
                    binding.tvResendOTP.visibility = View.VISIBLE
                    binding.tvResendOTPText.visibility = View.GONE
                    binding.tvRemainingTime.visibility = View.GONE
                    binding.etFirstDigit.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etSecondDigit.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etThirdDigit.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding.etFourthDigit.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setIntent(value: String) {
        val intent = Intent(this@OTPActivity, SecurityPinActivity::class.java)
        intent.putExtra("headerValue", value)
        startActivity(intent)
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (binding.etFirstDigit.isFocused) {
                if (binding.etFirstDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etSecondDigit.requestFocus()
                }
            } else if (binding.etSecondDigit.isFocused) {
                if (binding.etSecondDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etThirdDigit.requestFocus()
                }
            } else if (binding.etThirdDigit.isFocused) {
                if (binding.etThirdDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etFourthDigit.requestFocus()
                }
            } else if (binding.etFourthDigit.isFocused) {
                if (binding.etFirstDigit.text.toString()
                        .isNotEmpty() && binding.etSecondDigit.text.toString()
                        .isNotEmpty()
                    && binding.etThirdDigit.text.toString()
                        .isNotEmpty() && binding.etFourthDigit.text.toString()
                        .isNotEmpty()
                ) {
                    if (binding.etFirstDigit.text.toString()
                            .equals("1") && binding.etSecondDigit.text.toString()
                            .equals("2")
                        && binding.etThirdDigit.text.toString()
                            .equals("3") && binding.etFourthDigit.text.toString()
                            .equals("4")
                    ) {
                        //Toast.makeText(this, "Successs", Toast.LENGTH_LONG).show()

                        if (value == "Login") {
                            setIntent(value)
                        } else if (value == "ForgotPin") {
                            setIntent(value)
                        } else {
                            val intent = Intent(this@OTPActivity, ScooterDetailActivity::class.java)
                            intent.putExtra("fromFragment", value)
                            startActivity(intent)
                        }
                        binding.tvOtpInvalid.visibility = View.GONE
                        binding.tvResendOTP.visibility = View.GONE
                        binding.tvResendOTPText.visibility = View.GONE
                        binding.tvRemainingTime.visibility = View.GONE
                    } else {
                        binding.tvOtpInvalid.visibility = View.VISIBLE
                        binding.tvResendOTP.visibility = View.VISIBLE
                        binding.tvResendOTPText.visibility = View.GONE
                        binding.tvRemainingTime.visibility = View.GONE
                        binding.etFirstDigit.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding.etSecondDigit.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding.etThirdDigit.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding.etFourthDigit.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                    }
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.tvVerify.background = resources.getDrawable(R.drawable.ic_button_bg)
            binding.tvVerify.setTextColor(resources.getColor(R.color.colorNero))
            binding.etFirstDigit.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding.etSecondDigit.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding.etThirdDigit.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding.etFourthDigit.background =
                resources.getDrawable(R.drawable.radius_bg)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            when (this.currentFocus!!.id) {
                binding.etFourthDigit.id -> {
                    binding.etThirdDigit.requestFocus()
                    binding.etThirdDigit.setText("")
                }
                binding.etThirdDigit.id -> {
                    binding.etSecondDigit.requestFocus()
                    binding.etSecondDigit.setText("")
                }
                binding.etSecondDigit.id -> {
                    binding.etFirstDigit.requestFocus()
                    binding.etFirstDigit.setText("")
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
        }
        return true
    }

    private fun passingFocusOfEditTextToNext() {
        binding.etFirstDigit.addTextChangedListener(watcher)
        binding.etSecondDigit.addTextChangedListener(watcher)
        binding.etThirdDigit.addTextChangedListener(watcher)
        binding.etFourthDigit.addTextChangedListener(watcher)
    }
}
