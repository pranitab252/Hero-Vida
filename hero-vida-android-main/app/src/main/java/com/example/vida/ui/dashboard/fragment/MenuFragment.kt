package com.example.vida.ui.dashboard.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.vida.R
import com.example.vida.databinding.FragmentMenuBinding
import com.example.vida.ui.dashboard.MyGarbageActivity
import com.example.vida.ui.dashboard.NotificationSettingsActivity
import com.example.vida.ui.dashboard.SecuritySettingActivity
import com.example.vida.utils.extension.Functions
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = _binding!!.root
        /*Functions.statusColor(
            requireActivity(),
            ContextCompat.getColor(requireActivity().applicationContext, R.color.transparent)
        )*/
//        Functions.makeTransperantStatusBar(requireActivity(), true)

        initView()

        return root
    }

    private fun initView() {
        binding.tvSubscribe.setOnClickListener {
            binding.tvSubscribe.hide()
            binding.tvSaveRupees.hide()
            binding.tvPrice.show()
            binding.tvMeterPrice.show()
        }

        binding.rlNotificationSetting.setOnClickListener {
            val intent = Intent(requireContext(), NotificationSettingsActivity::class.java)
            startActivity(intent)
        }
        binding.rlSecuritySetting.setOnClickListener {
            val intent = Intent(requireContext(), SecuritySettingActivity::class.java)
            startActivity(intent)
        }
        binding.rlGarbage.setOnClickListener {
            val intent = Intent(requireContext(), MyGarbageActivity::class.java)
            startActivity(intent)
        }
    }
}