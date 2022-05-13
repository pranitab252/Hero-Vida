package com.example.vida.ui.dashboard.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vida.R
import com.example.vida.databinding.FragmentAllScooterBinding
import com.example.vida.ui.dashboard.ScooterInformationActivity
import com.example.vida.ui.register.AddScooterActivity

class AllScooterFragment : Fragment() {
    private lateinit var _binding: FragmentAllScooterBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllScooterBinding.inflate(inflater, container, false)
        val root: View = _binding.root
        initializeView()
        return root
    }

    private fun initializeView() {
        binding.tvViewDetails.setOnClickListener {
            val intent = Intent(requireActivity(), ScooterInformationActivity::class.java)
            startActivity(intent)
        }
        binding.tvAddMyAnotherScooter.setOnClickListener {
            val intent = Intent(requireActivity(), AddScooterActivity::class.java)
            intent.putExtra("fromActivity", "ScooterFragment")
            startActivity(intent)
        }
    }
}