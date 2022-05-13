package com.example.vida.ui.personaldetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vida.R

class ImagePreviewActivity : AppCompatActivity() {
    private lateinit var mContext: AppCompatActivity
    private lateinit var btnTakeAnother: TextView
    private lateinit var btnConfirm: TextView
    private lateinit var ivBack: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        mContext = this
        initializeView()

    }

    private fun initializeView() {
        btnTakeAnother = findViewById(R.id.btnTakeAnother)
        btnConfirm = findViewById(R.id.btnConfirm)
        ivBack = findViewById(R.id.ivBack)

        btnConfirm.setOnClickListener {
            finish()
        }

        ivBack.setOnClickListener {
            finish()
        }
    }
}