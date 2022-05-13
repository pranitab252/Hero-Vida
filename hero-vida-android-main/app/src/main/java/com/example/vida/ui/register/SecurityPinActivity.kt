package com.example.vida.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.vida.R
import com.example.vida.databinding.ActivitySecurityPinBinding
import com.example.vida.ui.dashboard.DashboardActivity
import com.example.vida.utils.extension.afterTextChanged
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.example.vida.utils.extension.showToastSuccess
import com.google.android.material.bottomsheet.BottomSheetDialog

class SecurityPinActivity : AppCompatActivity() {
    private var binding: ActivitySecurityPinBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecurityPinBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initView()
        passingFocusOfEditTextToNext()
        passingFocusOfEditTextToNextConform()

    }

    private fun initView() {
        val value: String = intent.getStringExtra("headerValue").toString()
        when (value) {
            "Login" -> {
                binding!!.rlHeaderSecurity.show()
                binding!!.tvNewPin.hide()
            }
            "ForgotPin" -> {
                binding!!.rlHeaderSecurity.hide()
                binding!!.tvNewPin.show()
            }
        }

        binding!!.imgBackArrow.setOnClickListener {
            finish()
        }

        binding!!.tvContinue.setOnClickListener {
            if (binding!!.etFirstDigit.text.toString()
                    .isNotEmpty() && binding!!.etSecondDigit.text.toString()
                    .isNotEmpty()
                && binding!!.etThirdDigit.text.toString()
                    .isNotEmpty() && binding!!.etFourthDigit.text.toString()
                    .isNotEmpty()
            ) {
                if (binding!!.etFirstDigitConform.text.toString()
                        .isNotEmpty() && binding!!.etSecondDigitConform.text.toString()
                        .isNotEmpty()
                    && binding!!.etThirdDigitConform.text.toString()
                        .isNotEmpty() && binding!!.etFourthDigitConform.text.toString()
                        .isNotEmpty()
                ) {
                    if (
                        binding!!.etFirstDigitConform.text.toString() == binding!!.etFirstDigit.text.toString() && binding!!.etSecondDigitConform.text.toString() == binding!!.etSecondDigit.text.toString()
                        && binding!!.etThirdDigitConform.text.toString() == binding!!.etThirdDigit.text.toString() && binding!!.etFourthDigitConform.text.toString() == binding!!.etFourthDigit.text.toString()
                    ) {
                        if (value == "ForgotPin") {
                            startActivity(
                                Intent(
                                    this@SecurityPinActivity,
                                    DashboardActivity::class.java
                                )
                            )
                        } else {
                            showToastSuccess("Pin set successfully! ", "", true)
                            showBottomSheetDialog()
                        }
                    } else {
                        binding!!.etFirstDigit.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding!!.etSecondDigit.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding!!.etThirdDigit.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding!!.etFourthDigit.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding!!.etFirstDigitConform.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding!!.etSecondDigitConform.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding!!.etThirdDigitConform.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding!!.etFourthDigitConform.background =
                            resources.getDrawable(R.drawable.edittext_code_error_bg)
                        binding!!.tvPinDontMatch.show()
                        binding!!.tvSkipPin.show()
                        binding!!.tvContinue.background =
                            resources.getDrawable(R.drawable.button_unselect_bg)
                        binding!!.tvContinue.setTextColor(resources.getColor(R.color.colorWhite))
                    }
                } else {
                    Toast.makeText(this, "Please Enter Confirm OTP", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_LONG).show()
            }
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
                binding!!.etFourthDigitConform.id -> {
                    binding!!.etThirdDigitConform.requestFocus()
                    binding!!.etThirdDigitConform.setText("")
                }
                binding!!.etThirdDigitConform.id -> {
                    binding!!.etSecondDigitConform.requestFocus()
                    binding!!.etSecondDigitConform.setText("")
                }
                binding!!.etSecondDigitConform.id -> {
                    binding!!.etFirstDigitConform.requestFocus()
                    binding!!.etFirstDigitConform.setText("")
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
        }
        return true
    }

    private fun passingFocusOfEditTextToNext() {
        binding!!.etFirstDigit.afterTextChanged({
            if (binding!!.etFirstDigit.isFocused) {
                if (binding!!.etFirstDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etSecondDigit.requestFocus()
                }
            }
        }, {})
        binding!!.etSecondDigit.afterTextChanged({
            if (binding!!.etSecondDigit.isFocused) {
                if (binding!!.etSecondDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etThirdDigit.requestFocus()
                }
            }
        }, {})
        binding!!.etThirdDigit.afterTextChanged({
            if (binding!!.etThirdDigit.isFocused) {
                if (binding!!.etThirdDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etFourthDigit.requestFocus()
                }
            }
        }, {})
        binding!!.etFourthDigit.afterTextChanged({}, {})
    }

    private fun passingFocusOfEditTextToNextConform() {
        binding!!.etFirstDigitConform.afterTextChanged({
            if (binding!!.etFirstDigitConform.isFocused) {
                if (binding!!.etFirstDigitConform.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etSecondDigitConform.requestFocus()
                }
            }
        }, {})
        binding!!.etSecondDigitConform.afterTextChanged({
            if (binding!!.etSecondDigitConform.isFocused) {
                if (binding!!.etSecondDigitConform.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etThirdDigitConform.requestFocus()
                }
            }
        }, {})
        binding!!.etThirdDigitConform.afterTextChanged({
            if (binding!!.etThirdDigitConform.isFocused) {
                if (binding!!.etThirdDigitConform.text.toString().length == 1) //size as per your requirement
                {
                    binding!!.etFourthDigitConform.requestFocus()
                }
            }
        }, {})
        binding!!.etFourthDigitConform.afterTextChanged({}, {
            binding!!.etFirstDigit.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding!!.etSecondDigit.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding!!.etThirdDigit.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding!!.etFourthDigit.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding!!.etFirstDigitConform.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding!!.etSecondDigitConform.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding!!.etThirdDigitConform.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding!!.etFourthDigitConform.background =
                resources.getDrawable(R.drawable.radius_bg)
            binding!!.tvPinDontMatch.hide()
            binding!!.tvSkipPin.hide()
            binding!!.tvContinue.background = resources.getDrawable(R.drawable.ic_button_bg)
            binding!!.tvContinue.setTextColor(resources.getColor(R.color.colorNero))
        })
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        bottomSheetDialog.setContentView(R.layout.face_id_authtentication_bottom_sheet)
        val tvNo = bottomSheetDialog.findViewById<TextView>(R.id.tvNo)
        val tvYes = bottomSheetDialog.findViewById<TextView>(R.id.tvYes)

        tvNo?.setOnClickListener {
            val intent = Intent(this@SecurityPinActivity, DashboardActivity::class.java)
            intent.putExtra("setPinSuccess",true)
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }
        tvYes?.setOnClickListener {
            val intent = Intent(this@SecurityPinActivity, DashboardActivity::class.java)
            intent.putExtra("setPinSuccess",true)
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
        }

        bottomSheetDialog.show()
    }
}
