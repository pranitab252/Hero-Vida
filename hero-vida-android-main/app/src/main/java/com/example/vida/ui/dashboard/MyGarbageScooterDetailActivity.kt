package com.example.vida.ui.dashboard

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.vida.R
import com.example.vida.databinding.ActivityMyGarbageScooterDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MyGarbageScooterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyGarbageScooterDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGarbageScooterDetailBinding.inflate(layoutInflater)
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
        val tabLayout: TabLayout = binding.tlMyGarbageScooter
        tabLayout.addTab(tabLayout.newTab().setText("All Scooter"))
        tabLayout.addTab(tabLayout.newTab().setText("Manage Users"))

        val selectedTab = tabLayout.getTabAt(0)

        val navController = findNavController(R.id.navHostFragmentMyGarbageDetail)

        val navOptions: NavOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(navController.graph.startDestinationId, false)
            .build()

        ResourcesCompat.getFont(selectedTab!!.view.context, R.font.poppins_semibold)?.let {
            setTabTypeface(
                tab = selectedTab,
                typeface = it
            )
        }
        binding.tlMyGarbageScooter.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 ->{
                        navController.popBackStack()
                        navController.navigate(R.id.navigation_all_scooter)
                    }
                    1 ->{
                        navController.popBackStack()
                        navController.navigate(R.id.navigation_manage_users)
                    }
                }
                ResourcesCompat.getFont(tab.view.context, R.font.poppins_semibold)?.let {
                    setTabTypeface(
                        tab = tab,
                        typeface = it
                    )
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

    private fun setTabTypeface(tab: TabLayout.Tab, typeface: Typeface) {
        for (i in 0 until tab.view.childCount) {
            val tabViewChild: View = tab.view.getChildAt(i)
            if (tabViewChild is TextView) (tabViewChild as TextView).typeface = typeface
        }
    }
}