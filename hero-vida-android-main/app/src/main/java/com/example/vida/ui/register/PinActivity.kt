package com.example.vida.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.vida.R
import com.example.vida.databinding.ActivityPinBinding
import com.example.vida.ui.dashboard.DashboardActivity

class PinActivity : AppCompatActivity() {
    private var binding: ActivityPinBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initView()
        passingFocusOfEditTextToNext()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {
        binding!!.tvPinVerify.setOnClickListener {
            if (binding!!.etFirstDigit.text.toString()
                    .isNotEmpty() && binding!!.etSecondDigit.text.toString()
                    .isNotEmpty()
                && binding!!.etThirdDigit.text.toString()
                    .isNotEmpty() && binding!!.etFourthDigit.text.toString()
                    .isNotEmpty()
            ) {
                if (binding!!.etFirstDigit.text.toString()
                        .equals("1") && binding!!.etSecondDigit.text.toString()
                        .equals("2")
                    && binding!!.etThirdDigit.text.toString()
                        .equals("3") && binding!!.etFourthDigit.text.toString()
                        .equals("4")
                ) {
                    //Toast.makeText(this, "Pin submitted successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@PinActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    binding!!.tvWrongPin.visibility = View.GONE
                    binding!!.tvForgotPin.setTextColor(resources.getColor(R.color.colorPrimary))
                } else {
                    binding!!.tvWrongPin.visibility = View.VISIBLE
                    binding!!.tvForgotPin.setTextColor(resources.getColor(R.color.colorPinError))
                    binding!!.etFirstDigit.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding!!.etSecondDigit.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding!!.etThirdDigit.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                    binding!!.etFourthDigit.background =
                        resources.getDrawable(R.drawable.edittext_code_error_bg)
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_LONG).show()
            }
        }

        binding!!.tvForgotPin.setOnClickListener {
            val intent = Intent(this@PinActivity, RegisterActivity::class.java)
            intent.putExtra("loginOrRegister", "ForgotPin")
            startActivity(intent)
        }
    }

    private val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (binding!!.etFirstDigit.isFocused) {
                if (binding!!.etFirstDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etSecondDigit.requestFocus()
                }
            } else if (binding!!.etSecondDigit.isFocused) {
                if (binding!!.etSecondDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etThirdDigit.requestFocus()
                }
            } else if (binding!!.etThirdDigit.isFocused) {
                if (binding!!.etThirdDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etFourthDigit.requestFocus()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding!!.tvPinVerify.background = resources.getDrawable(R.drawable.ic_button_bg)
            binding!!.tvPinVerify.setTextColor(resources.getColor(R.color.colorNero))
            binding!!.tvWrongPin.visibility = View.GONE
            binding!!.tvForgotPin.setTextColor(resources.getColor(R.color.colorPrimary))

            binding!!.etFirstDigit.background =
                resources.getDrawable(R.drawable.edittext_button_state_shape)
            binding!!.etSecondDigit.background =
                resources.getDrawable(R.drawable.edittext_button_state_shape)
            binding!!.etThirdDigit.background =
                resources.getDrawable(R.drawable.edittext_button_state_shape)
            binding!!.etFourthDigit.background =
                resources.getDrawable(R.drawable.edittext_button_state_shape)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            when (this.currentFocus!!.id) {
                binding!!.etFourthDigit.id -> {
                    binding!!.etThirdDigit.requestFocus()
                    binding!!.etThirdDigit.setText("")
                }
                binding!!.etThirdDigit.id -> {
                    binding!!.etSecondDigit.requestFocus()
                    binding!!.etSecondDigit.setText("")
                }
                binding!!.etSecondDigit.id -> {
                    binding!!.etFirstDigit.requestFocus()
                    binding!!.etFirstDigit.setText("")
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
        }
        return true
    }

    private fun passingFocusOfEditTextToNext() {
        binding!!.etFirstDigit.addTextChangedListener(watcher)
        binding!!.etSecondDigit.addTextChangedListener(watcher)
        binding!!.etThirdDigit.addTextChangedListener(watcher)
        binding!!.etFourthDigit.addTextChangedListener(watcher)
    }
}
