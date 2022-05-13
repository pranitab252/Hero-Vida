package com.example.vida.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.vida.R
import com.example.vida.common.CCPCustomTalkBackProvider
import com.example.vida.databinding.ActivityRegisterBinding
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.hbb20.CountryCodePicker

class RegisterActivity : AppCompatActivity() {
    private var binding: ActivityRegisterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initView()
        setCustomTalkBackProvider()
        passingFocusOfEditTextToNext()

    }

    private fun initView() {
        val registerTextValue: String = intent.getStringExtra("loginOrRegister").toString()
        when (registerTextValue) {
            "Register" -> {
                binding!!.tvRegister.text = resources.getString(R.string.register)
                binding!!.imgLogoBlack.show()
                binding!!.tvForgotPinShow.hide()
                binding!!.tvResetPin.hide()
                binding!!.llAlreadyLoginNow.show()
                binding!!.llNewRegisterNow.hide()
                binding!!.imgVidaBlackBottom.hide()
                binding!!.llHavingTrouble.show()
                binding!!.tvRegister.show()
            }
            "Login" -> {
                binding!!.tvRegister.text = resources.getString(R.string.login)
                binding!!.imgLogoBlack.show()
                binding!!.tvForgotPinShow.hide()
                binding!!.tvResetPin.hide()
                binding!!.llAlreadyLoginNow.hide()
                binding!!.llNewRegisterNow.hide()
                binding!!.imgVidaBlackBottom.hide()
                binding!!.llHavingTrouble.hide()
                binding!!.tvRegister.visibility
            }
            "ForgotPin" -> {
                binding!!.imgLogoBlack.hide()
                binding!!.tvForgotPinShow.show()
                binding!!.tvResetPin.show()
                binding!!.llAlreadyLoginNow.hide()
                binding!!.llNewRegisterNow.show()
                binding!!.imgVidaBlackBottom.show()
                binding!!.llHavingTrouble.show()
                binding!!.tvRegister.hide()
            }
        }
        binding!!.tvSendOTP.setOnClickListener {
            when (registerTextValue) {
                "Register" -> {
                    setIntent("register")
                }
                "Login" -> {

                    setIntent("Login")
                }
                "ForgotPin" -> {

                    setIntent("ForgotPin")
                }
            }
        }

        binding!!.tvLoginNow.setOnClickListener {
            val intent = Intent(this@RegisterActivity, RegisterActivity::class.java)
            intent.putExtra("loginOrRegister", "Login")
            startActivity(intent)
        }
//        countryCodePicker.registerCarrierNumberEditText(et_enterNumber)
    }

    private fun setIntent(value: String) {
        val intent = Intent(this@RegisterActivity, OTPActivity::class.java)
        intent.putExtra("RegisterToOtp", value)
        startActivity(intent)
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding!!.tvSendOTP.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_button_bg, null)
            binding!!.tvSendOTP.setTextColor(resources.getColor(R.color.colorNero))
            binding!!.tvSendOTP.isEnabled = true

        }

        override fun afterTextChanged(s: Editable?) {
        }

    }

    private fun setCustomTalkBackProvider() {
        binding!!.countryPicker.setTalkBackTextProvider(CCPCustomTalkBackProvider())
    }

    private fun passingFocusOfEditTextToNext() {
        binding!!.etEnterNumber.addTextChangedListener(watcher)
    }
}
