package com.example.vida.ui.dashboard.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vida.R
import com.example.vida.databinding.FragmentHealthBinding
import com.example.vida.ui.dashboard.BatteryActivity
import com.example.vida.ui.dashboard.DashboardActivity
import com.example.vida.ui.dashboard.DiagnosisProgressActivity
import com.example.vida.ui.dashboard.DiagnosticReportActivity

class HealthFragment : Fragment() {
    private lateinit var _binding: FragmentHealthBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initializeView()
        return root
    }

    private fun initializeView() {
        binding.tvRun.setOnClickListener {
            val intent = Intent(requireActivity(), DiagnosisProgressActivity::class.java)
            startActivity(intent)
        }
        binding.rlBatteryHealth.setOnClickListener {
            val intent = Intent(requireActivity(), BatteryActivity::class.java)
            startActivity(intent)
        }
    }
}