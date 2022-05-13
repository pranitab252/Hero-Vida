package com.example.vida.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.R
import com.example.vida.databinding.ActivityManageSubscriptionConfirmBinding
import com.example.vida.databinding.UpgradePlanReceiptSheetBinding
import com.example.vida.databinding.UpgradePlanSheetBinding
import com.example.vida.ui.dashboard.adapter.ManageSubscriptionUpgradePlaneAdapter
import com.example.vida.ui.dashboard.adapter.SubscriptionUpgradePlan
import com.google.android.material.bottomsheet.BottomSheetDialog

class ManageSubscriptionConfirmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageSubscriptionConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageSubscriptionConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.ivEditPlan.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        val bottomBinding = UpgradePlanSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomBinding.root)
        bottomBinding.ivCrossSheet.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val manageSubscriptionList: ArrayList<SubscriptionUpgradePlan> = ArrayList()
        manageSubscriptionList.add(SubscriptionUpgradePlan("Umbrella Plus",
            "Recommended & Includes all plans",
            "700",
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
            ManageSubscriptionUpgradePlaneAdapter(this, manageSubscriptionList, false)
        bottomBinding.rvUpgradePlanSheet.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bottomBinding.rvUpgradePlanSheet.adapter = subscriptionUpgradeAdapter
        subscriptionUpgradeAdapter.setOnItemClickListener(object :
            ManageSubscriptionUpgradePlaneAdapter.OnItemClickListener {
            override fun onItemClick() {
                showBottomSheetDialog()
            }

        })

        bottomSheetDialog.show()
    }
}