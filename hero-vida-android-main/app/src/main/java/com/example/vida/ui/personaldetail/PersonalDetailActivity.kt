package com.example.vida.ui.personaldetail

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import com.example.vida.R
import com.example.vida.common.CCPCustomTalkBackProvider
import com.example.vida.ui.register.SecurityPinActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hbb20.CountryCodePicker
import java.text.SimpleDateFormat
import java.util.*


class PersonalDetailActivity : AppCompatActivity() {
    private lateinit var countryCodePicker: CountryCodePicker
    private lateinit var mContext: AppCompatActivity
    private lateinit var btnAdd: TextView
    private lateinit var etDob: EditText
    private lateinit var rlUploadPhoto: RelativeLayout
    private lateinit var simpleSpinner: AppCompatSpinner
    private lateinit var stateSpinner: AppCompatSpinner
    private val myCalendar: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_detail)

        mContext = this
        initializeView()
        setCustomTalkBackProvider()
        setUpSpinnerAdapter()
        setUpSpinnerStateAdapter()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initializeView() {
        btnAdd = findViewById(R.id.btnContinue)
        etDob = findViewById(R.id.et_dob)
        rlUploadPhoto = findViewById(R.id.rlUploadPhoto)
        countryCodePicker = findViewById(R.id.countryPicker)
        simpleSpinner = findViewById(R.id.simpleSpinner)
        stateSpinner = findViewById(R.id.stateSpinner)


        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }

        etDob.setOnClickListener(View.OnClickListener {
            DatePickerDialog(
                this@PersonalDetailActivity,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        })

        btnAdd.setOnClickListener {
            val intent = Intent(this@PersonalDetailActivity, SecurityPinActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        rlUploadPhoto.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        etDob.setText(dateFormat.format(myCalendar.getTime()))
    }

    private fun setUpSpinnerAdapter() {
        val bloodList = arrayListOf<SpinnerItems>()

        bloodList.add(SpinnerItems("A+ ve", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("B+ ve", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("O+ ve", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("AB+ ve", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("A- ve", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("B- ve", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("O- ve", "R.drawable.ic_profile_image"))


        val customDropDownAdapter = CustomSpinnerAdapter(this, bloodList)
        simpleSpinner.adapter = customDropDownAdapter

        var iCurrentSelection: Int = simpleSpinner.selectedItemPosition

       /* simpleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (iCurrentSelection == i) {
                    adapterView!!.background = resources.getDrawable(R.drawable.phone_code_bg)
                }else{
                    adapterView!!.background = resources.getDrawable(R.drawable.phone_code_bg)
                }
                iCurrentSelection = i
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }*/
    }

    private fun setUpSpinnerStateAdapter() {
        val stateList = arrayListOf<SpinnerItems>()

        stateList.add(SpinnerItems("Maharashtra", "R.drawable.ic_profile_image"))
        stateList.add(SpinnerItems("Maharashtra B", "R.drawable.ic_profile_image"))
        stateList.add(SpinnerItems("Maharashtra Avenue", "R.drawable.ic_profile_image"))

        val customDropDownAdapter = CustomSpinnerAdapter(this, stateList)
        stateSpinner.adapter = customDropDownAdapter
    }

    private fun setCustomTalkBackProvider() {
        countryCodePicker.setTalkBackTextProvider(CCPCustomTalkBackProvider())
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout)
        val btnChoose = bottomSheetDialog.findViewById<TextView>(R.id.btnChoose)
        val llTakePhoto = bottomSheetDialog.findViewById<LinearLayout>(R.id.llTakePhoto)
        val close = bottomSheetDialog.findViewById<ImageView>(R.id.ivClose)

        btnChoose?.setOnClickListener {
            gotoImagePreviewScreen()
            bottomSheetDialog.dismiss()
        }

        llTakePhoto?.setOnClickListener {
            gotoImagePreviewScreen()
            bottomSheetDialog.dismiss()
        }

        close?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
        }

        bottomSheetDialog.show()
    }

    private fun gotoImagePreviewScreen() {
        val intent = Intent(this@PersonalDetailActivity, ImagePreviewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }


}