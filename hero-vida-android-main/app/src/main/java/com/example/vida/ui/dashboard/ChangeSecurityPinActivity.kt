package com.example.vida.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.vida.R
import com.example.vida.databinding.ActivityChangeSecurityPinBinding
import com.example.vida.ui.register.ScooterDetailActivity
import com.example.vida.utils.extension.afterTextChanged
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.example.vida.utils.extension.showToastSuccess

class ChangeSecurityPinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeSecurityPinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeSecurityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        passingFocusOfEditTextToNext()
        passingFocusOfEditTextToNextConform()
        passingFocusOfEditTextToNextCurrent()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.tvContinue.setOnClickListener {
            if (binding.etCurrentFirstDigit.text.toString()
                    .isNotEmpty() && binding.etCurrentSecondDigit.text.toString()
                    .isNotEmpty()
                && binding.etCurrentThirdDigit.text.toString()
                    .isNotEmpty() && binding.etCurrentFourthDigit.text.toString()
                    .isNotEmpty()
            ) {
                if (binding.etCurrentFirstDigit.text.toString()
                        .equals("1") && binding.etCurrentSecondDigit.text.toString()
                        .equals("2")
                    && binding.etCurrentThirdDigit.text.toString()
                        .equals("3") && binding.etCurrentFourthDigit.text.toString()
                        .equals("4")
                ) {
                    if (binding.etFirstDigit.text.toString()
                            .isNotEmpty() && binding.etSecondDigit.text.toString()
                            .isNotEmpty()
                        && binding.etThirdDigit.text.toString()
                            .isNotEmpty() && binding.etFourthDigit.text.toString()
                            .isNotEmpty()
                    ) {
                        if (binding.etFirstDigitConform.text.toString()
                                .isNotEmpty() && binding.etSecondDigitConform.text.toString()
                                .isNotEmpty()
                            && binding.etThirdDigitConform.text.toString()
                                .isNotEmpty() && binding.etFourthDigitConform.text.toString()
                                .isNotEmpty()
                        ) {
                            if (
                                binding.etFirstDigitConform.text.toString() == binding.etFirstDigit.text.toString() && binding.etSecondDigitConform.text.toString() == binding.etSecondDigit.text.toString()
                                && binding.etThirdDigitConform.text.toString() == binding.etThirdDigit.text.toString() && binding.etFourthDigitConform.text.toString() == binding.etFourthDigit.text.toString()
                            ) {
                                val intent = Intent(
                                    this@ChangeSecurityPinActivity,
                                    SecuritySettingActivity::class.java
                                )
                                intent.putExtra("changePinSuccess", true)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                finish()
                            } else {
                                binding.etFirstDigit.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.edittext_code_error_bg,
                                        null
                                    )
                                binding.etSecondDigit.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.edittext_code_error_bg,
                                        null
                                    )
                                binding.etThirdDigit.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.edittext_code_error_bg,
                                        null
                                    )
                                binding.etFourthDigit.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.edittext_code_error_bg,
                                        null
                                    )
                                binding.etFirstDigitConform.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.edittext_code_error_bg,
                                        null
                                    )
                                binding.etSecondDigitConform.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.edittext_code_error_bg,
                                        null
                                    )
                                binding.etThirdDigitConform.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.edittext_code_error_bg,
                                        null
                                    )
                                binding.etFourthDigitConform.background =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.edittext_code_error_bg,
                                        null
                                    )
                                binding.tvPinDontMatch.show()
                            }
                        } else {
                            Toast.makeText(this, "Please Enter Confirt New Pin", Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Please Enter New Pin", Toast.LENGTH_LONG).show()
                    }

                } else {
                    binding.etCurrentFirstDigit.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.edittext_code_error_bg,
                            null
                        )
                    binding.etCurrentSecondDigit.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.edittext_code_error_bg,
                            null
                        )
                    binding.etCurrentThirdDigit.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.edittext_code_error_bg,
                            null
                        )
                    binding.etCurrentFourthDigit.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.edittext_code_error_bg,
                            null
                        )
                }
            } else {
                Toast.makeText(this, "Please Enter Current PIN", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            when (this.currentFocus!!.id) {
                binding.etCurrentFourthDigit.id -> {
                    binding.etCurrentThirdDigit.requestFocus()
                    binding.etCurrentThirdDigit.setText("")
                }
                binding.etCurrentThirdDigit.id -> {
                    binding.etCurrentSecondDigit.requestFocus()
                    binding.etCurrentSecondDigit.setText("")
                }
                binding.etCurrentSecondDigit.id -> {
                    binding.etCurrentFirstDigit.requestFocus()
                    binding.etCurrentFirstDigit.setText("")
                }

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
                binding.etFourthDigitConform.id -> {
                    binding.etThirdDigitConform.requestFocus()
                    binding.etThirdDigitConform.setText("")
                }
                binding.etThirdDigitConform.id -> {
                    binding.etSecondDigitConform.requestFocus()
                    binding.etSecondDigitConform.setText("")
                }
                binding.etSecondDigitConform.id -> {
                    binding.etFirstDigitConform.requestFocus()
                    binding.etFirstDigitConform.setText("")
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
        }
        return true
    }

    private fun passingFocusOfEditTextToNextCurrent() {
        binding.etCurrentFirstDigit.afterTextChanged({
            if (binding.etCurrentFirstDigit.isFocused) {
                if (binding.etCurrentFirstDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etCurrentSecondDigit.requestFocus()
                }
            }
        }, {})
        binding.etCurrentSecondDigit.afterTextChanged({
            if (binding.etCurrentSecondDigit.isFocused) {
                if (binding.etCurrentSecondDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etCurrentThirdDigit.requestFocus()
                }
            }
        }, {})
        binding.etCurrentThirdDigit.afterTextChanged({
            if (binding.etCurrentThirdDigit.isFocused) {
                if (binding.etCurrentThirdDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etCurrentFourthDigit.requestFocus()
                }
            }
        }, {})
        binding.etCurrentFourthDigit.afterTextChanged({}, {
            binding.etCurrentFirstDigit.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etCurrentSecondDigit.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etCurrentThirdDigit.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etCurrentFourthDigit.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
        })
    }

    private fun passingFocusOfEditTextToNext() {
        binding.etFirstDigit.afterTextChanged({
            if (binding.etFirstDigit.isFocused) {
                if (binding.etFirstDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etSecondDigit.requestFocus()
                }
            }
        }, {})
        binding.etSecondDigit.afterTextChanged({
            if (binding.etSecondDigit.isFocused) {
                if (binding.etSecondDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etThirdDigit.requestFocus()
                }
            }
        }, {})
        binding.etThirdDigit.afterTextChanged({
            if (binding.etThirdDigit.isFocused) {
                if (binding.etThirdDigit.text.toString().length == 1) //size as per your requirement
                {
                    binding.etFourthDigit.requestFocus()
                }
            }
        }, {})
        binding.etFourthDigit.afterTextChanged({}, {})
    }

    private fun passingFocusOfEditTextToNextConform() {
        binding.etFirstDigitConform.afterTextChanged({
            if (binding.etFirstDigitConform.isFocused) {
                if (binding.etFirstDigitConform.text.toString().length == 1) //size as per your requirement
                {
                    binding.etSecondDigitConform.requestFocus()
                }
            }
        }, {})
        binding.etSecondDigitConform.afterTextChanged({
            if (binding.etSecondDigitConform.isFocused) {
                if (binding.etSecondDigitConform.text.toString().length == 1) //size as per your requirement
                {
                    binding.etThirdDigitConform.requestFocus()
                }
            }
        }, {})
        binding.etThirdDigitConform.afterTextChanged({
            if (binding.etThirdDigitConform.isFocused) {
                if (binding.etThirdDigitConform.text.toString().length == 1) //size as per your requirement
                {
                    binding.etFourthDigitConform.requestFocus()
                }
            }
        }, {})
        binding.etFourthDigitConform.afterTextChanged({}, {
            binding.etFirstDigit.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etSecondDigit.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etThirdDigit.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etFourthDigit.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etFirstDigitConform.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etSecondDigitConform.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etThirdDigitConform.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.etFourthDigitConform.background =
                ResourcesCompat.getDrawable(resources, R.drawable.radius_bg, null)
            binding.tvPinDontMatch.hide()
        })
    }
}