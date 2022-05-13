package com.example.vida.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vida.R
import com.example.vida.databinding.ActivityScooterInformationBinding
import com.example.vida.ui.dashboard.adapter.GarbageScooterAdapter
import com.example.vida.ui.dashboard.adapter.ScooterInformationVpAdapter
import com.example.vida.ui.dashboard.adapter.ScooterInformationVpItem

class ScooterInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScooterInformationBinding
    private var ScooterInformationAdapter: ScooterInformationVpAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScooterInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        initScooterViewPager()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initScooterViewPager() {
        val list: MutableList<ScooterInformationVpItem> = ArrayList()
        list.add(ScooterInformationVpItem(R.drawable.my_garbage_1, "TN 01 AB 0001", "324324234"))
        list.add(ScooterInformationVpItem(R.drawable.my_garbage_2, "MH 01 AB 0002", "324324234"))
        ScooterInformationAdapter = ScooterInformationVpAdapter(this, list)
        binding.vpScooterInformation.adapter = ScooterInformationAdapter
        binding.vpScooterInformation.currentItem = 1
        binding.tlScooterInformation.setupWithViewPager(binding.vpScooterInformation)
    }
}