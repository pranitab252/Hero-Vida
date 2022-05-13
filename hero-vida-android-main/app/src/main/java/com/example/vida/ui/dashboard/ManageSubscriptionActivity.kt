package com.example.vida.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.R
import com.example.vida.databinding.ActivityManageSubscriptionBinding
import com.example.vida.databinding.UpgradePlanReceiptSheetBinding
import com.example.vida.ui.dashboard.adapter.ManageSubscriptionUpgradePlaneAdapter
import com.example.vida.ui.dashboard.adapter.ScooterInformationVpAdapter
import com.example.vida.ui.dashboard.adapter.ScooterInformationVpItem
import com.example.vida.ui.dashboard.adapter.SubscriptionUpgradePlan
import com.google.android.material.bottomsheet.BottomSheetDialog

class ManageSubscriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageSubscriptionBinding
    private var manageSubscriptionAdapter: ScooterInformationVpAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        initMSViewPager()
        setRvSubscriptionUpgradePlan()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initMSViewPager() {
        val list: MutableList<ScooterInformationVpItem> = ArrayList()
        list.add(ScooterInformationVpItem(R.drawable.my_garbage_1, "TN 01 AB 0001", "324324234"))
        list.add(ScooterInformationVpItem(R.drawable.my_garbage_2, "MH 01 AB 0002", "324324234"))
        manageSubscriptionAdapter = ScooterInformationVpAdapter(this, list)
        binding.vpManageSubscription.adapter = manageSubscriptionAdapter
        binding.vpManageSubscription.currentItem = 1
        binding.tlManageSubscription.setupWithViewPager(binding.vpManageSubscription)
    }

    private fun setRvSubscriptionUpgradePlan() {
        val manageSubscriptionList: ArrayList<SubscriptionUpgradePlan> = ArrayList()
        manageSubscriptionList.add(SubscriptionUpgradePlan("Umbrella Plus",
            "Recommended & Includes all plans",
            "1,500",
            "200 people"))
        manageSubscriptionList.add(SubscriptionUpgradePlan("Connect Plus",
            "Monitor and control you vehicle on your hero app",
            "1,000",
            "100 people"))
        manageSubscriptionList.add(SubscriptionUpgradePlan("Recharge Plus",
            "Prepaid battery changing at 800+ charging points",
            "800",
            "200 people"))

        val subscriptionUpgradeAdapter =
            ManageSubscriptionUpgradePlaneAdapter(this, manageSubscriptionList, true)
        binding.rvSubscriptionUpgradePlan.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSubscriptionUpgradePlan.adapter = subscriptionUpgradeAdapter
        subscriptionUpgradeAdapter.setOnItemClickListener(object :
            ManageSubscriptionUpgradePlaneAdapter.OnItemClickListener {
            override fun onItemClick() {
                showBottomSheetDialog()
            }

        })
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        val bottomBinding = UpgradePlanReceiptSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomBinding.root)
        bottomBinding.ivCrossSheet.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomBinding.tvConfirmMakePayment.setOnClickListener {
            val intent = Intent(this@ManageSubscriptionActivity,
                ManageSubscriptionConfirmActivity::class.java)
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}