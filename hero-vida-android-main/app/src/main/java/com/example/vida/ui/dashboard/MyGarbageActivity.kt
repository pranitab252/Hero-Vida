package com.example.vida.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vida.R
import com.example.vida.databinding.ActivityMyGarbageBinding
import com.example.vida.ui.dashboard.adapter.GarbageScooterAdapter
import com.example.vida.ui.dashboard.adapter.GarbageScooterItem
import com.example.vida.ui.register.AddScooterActivity

class MyGarbageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyGarbageBinding
    private var garbageScooterAdapter: GarbageScooterAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGarbageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        initGarbageViewPager()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.rlScooterInformation.setOnClickListener {
            val intent = Intent(this, MyGarbageScooterDetailActivity::class.java)
            startActivity(intent)
        }
        binding.rlAddScooter.setOnClickListener {
            val intent = Intent(this, AddScooterActivity::class.java)
            intent.putExtra("fromActivity", "ScooterFragment")
            startActivity(intent)
        }
        binding.rlManageSubscription.setOnClickListener {
            val intent = Intent(this, ManageSubscriptionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initGarbageViewPager() {
        val list: MutableList<GarbageScooterItem> = ArrayList()
        list.add(GarbageScooterItem(R.drawable.my_garbage_1, "TN 01 AB 0001"))
        list.add(GarbageScooterItem(R.drawable.my_garbage_2, "MH 01 AB 0002"))
        garbageScooterAdapter = GarbageScooterAdapter(this, list)
        binding.vpGarbage.adapter = garbageScooterAdapter
        binding.tlGarbage.setupWithViewPager(binding.vpGarbage)
    }
}