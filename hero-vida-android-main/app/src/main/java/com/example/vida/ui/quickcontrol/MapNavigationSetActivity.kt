package com.example.vida.ui.quickcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.databinding.ActivityMapNavigationSetBinding
import com.example.vida.databinding.LayoutHeaderBinding
import com.example.vida.ui.dashboard.adapter.SearchResult
import com.example.vida.ui.dashboard.adapter.SearchResultAdapter

class MapNavigationSetActivity : AppCompatActivity() {
    private var binding: ActivityMapNavigationSetBinding? = null
    private lateinit var headerBinding: LayoutHeaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapNavigationSetBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        headerBinding = binding!!.icImmobilization

        setUpSearchResultAdapter()
    }

    private fun setUpSearchResultAdapter() {
        headerBinding.tvTitle.text = "Navigation"
        headerBinding.imgBackArrow.setOnClickListener{
            finish()
        }

        val searchResult: ArrayList<SearchResult> = ArrayList()
        searchResult.add(
            SearchResult(
                "2.8 km",
                "Murtajapur Charging Station",
                "DLF Phase 5, Gurgaon"
            )
        )
        searchResult.add(
            SearchResult(
                "12 km",
                "Murtajapur",
                "Aerocity, New Delhi, Delhi"
            )
        )
        searchResult.add(
            SearchResult(
                "215 km",
                "Ram nagar, Murtajapur",
                "near Civil Lines, Jaipur"
            )
        )

        val searchReachAdapter = SearchResultAdapter(this, searchResult)
        binding!!.rvSearchresult.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding!!.rvSearchresult.adapter = searchReachAdapter
    }
}