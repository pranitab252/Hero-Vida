package com.example.vida.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.R
import com.example.vida.databinding.ActivityDiagnosticReportBinding
import com.example.vida.ui.dashboard.adapter.DiagnosticIssuesElement
import com.example.vida.ui.dashboard.adapter.Stories
import com.example.vida.ui.dashboard.adapter.TodayRecyclerAdapter

class DiagnosticReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnosticReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosticReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        initDiagnosticAdapter()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.tvViewDetails.setOnClickListener {
            val intent = Intent(this, DiagnosticReportDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initDiagnosticAdapter() {
        val diagnostic: ArrayList<DiagnosticIssuesElement> = ArrayList()
        diagnostic.add(DiagnosticIssuesElement(R.drawable.ic_issues, "Head Light", ""))
        diagnostic.add(DiagnosticIssuesElement(R.drawable.ic_issues, "Mudflap Front/Rear", ""))
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_issues,
                "Shock Absorbers/Suspension",
                ""
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Engine Oil/Greecing Oil/Break Oil",
                ""
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Mudflap Front/Rear",
                ""
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Rear Tire Condition",
                ""
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Engine Oil/Greecing Oil/Break Oil",
                ""
            )
        )
        diagnostic.add(
            DiagnosticIssuesElement(
                R.drawable.ic_small_check,
                "Mudflap Front/Rear",
                ""
            )
        )
        val searchReachAdapter = DiagnosticRecyclerAdapter(this, diagnostic, false)
        binding.rvDiagnosticIssues.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvDiagnosticIssues.adapter = searchReachAdapter
    }
}