package com.example.vida

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vida.databinding.ScooterscreennewBinding
import com.example.vida.ui.quickcontrol.GeoFence.GeoFencelocnoneActivity

class GeoFenceStart : Fragment() {
    private lateinit var _binding: ScooterscreennewBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScooterscreennewBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initializeView()
        return root
    }

    private fun initializeView() {
        binding.imgclick.setOnClickListener {
            val intent = Intent(requireActivity(), GeoFencelocnoneActivity::class.java)
            startActivity(intent)
        }
    }
}