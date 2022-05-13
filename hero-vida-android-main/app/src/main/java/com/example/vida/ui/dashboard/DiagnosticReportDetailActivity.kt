package com.example.vida.ui.dashboard

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.R
import com.example.vida.databinding.ActivityDiagnosticReportDetailBinding
import com.example.vida.ui.dashboard.adapter.DiagnosticIssuesElement
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.google.android.material.tabs.TabLayout


class DiagnosticReportDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnosticReportDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosticReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        setTab()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun setTab() {
        val tabLayout: TabLayout = binding.tlDiagnosticReport
        tabLayout.addTab(tabLayout.newTab().setText("Issue(3)"))
        tabLayout.addTab(tabLayout.newTab().setText("Good(6)"))

        val selectedTab = tabLayout.getTabAt(0)
        if (tabLayout.selectedTabPosition == 0) {
            initDiagnosticAdapterIssue()
            binding.llDiagnosticSummary.show()
        }
        ResourcesCompat.getFont(selectedTab!!.view.context, R.font.poppins_semibold)?.let {
            setTabTypeface(
                tab = selectedTab,
                typeface = it
            )
        }
        binding.tlDiagnosticReport.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                ResourcesCompat.getFont(tab!!.view.context, R.font.poppins_semibold)?.let {
                    setTabTypeface(
                        tab = tab,
                        typeface = it
                    )
                }
                if (tabLayout.selectedTabPosition == 0) {
                    initDiagnosticAdapterIssue()
                    binding.llDiagnosticSummary.show()
                } else {
                    initDiagnosticAdapterGood()
                    binding.llDiagnosticSummary.hide()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                ResourcesCompat.getFont(tab!!.view.context, R.font.poppins_medium)?.let {
                    setTabTypeface(
                        tab = tab,
                        typeface = it
                    )
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun initDiagnosticAdapterIssue() {
        val diagnostic: ArrayList<DiagnosticIssuesElement> = ArrayList()
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_issues,
                "Head Light",
                "LED bulb of the hedlight isn’t working. Replace of LED bulb required."
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_issues,
                "Mudflap Front/Rear",
                "Battery drainage issue noticed. Immediate action required."
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_issues,
                "Shock Absorbers/Suspension",
                "Needs to be replaced soon, visit your nearest service center "
            )
        )

        val searchReachAdapter = DiagnosticRecyclerAdapter(this, diagnostic, true)
        binding.rvTabDiagnostic.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvTabDiagnostic.adapter = searchReachAdapter
    }

    private fun initDiagnosticAdapterGood() {
        val diagnostic: ArrayList<DiagnosticIssuesElement> = ArrayList()

        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Engine Oil/Greecing Oil/Break Oil",
                "Looks good. No action required!"
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Mudflap Front/Rear",
                "Looks good. No action required!"
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Rear Tire Condition",
                "Looks good. No action required!"
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Engine Oil/Greecing Oil/Break Oil",
                "Looks good. No action required!"
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Mudflap Front/Rear",
                "Looks good. No action required!"
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "MShock Absorbers/Suspension",
                "Looks good. No action required!"
            )
        )
        val searchReachAdapter = DiagnosticRecyclerAdapter(this, diagnostic, true)
        binding.rvTabDiagnostic.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvTabDiagnostic.adapter = searchReachAdapter
    }

    private fun setTabTypeface(tab: TabLayout.Tab, typeface: Typeface) {
        for (i in 0 until tab.view.childCount) {
            val tabViewChild: View = tab.view.getChildAt(i)
            if (tabViewChild is TextView) (tabViewChild as TextView).typeface = typeface
        }
    }
}