package com.example.vida.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vida.R
import com.example.vida.databinding.ActivityChargingPointBinding
import com.example.vida.ui.dashboard.adapter.ChargingPointITem
import com.example.vida.ui.dashboard.adapter.ChargingPointRvAdapter
import com.example.vida.utils.extension.Functions
import com.example.vida.utils.extension.afterTextChanged
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class ChargingPointActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mContext: AppCompatActivity
    private lateinit var binding: ActivityChargingPointBinding
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext=this
        initializeView()
        setUpGoogleMap()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.etSearch.afterTextChanged({}, {
            if (it.isEmpty()) {
                binding.rvChargingPoint.hide()
                binding.rlChargingPointMap.show()
            } else {
                setChargingPointRvAdapter()
                binding.rvChargingPoint.show()
                binding.rlChargingPointMap.hide()
                binding.rlSearchFilter.hide()
            }
        })
    }

    private fun setUpGoogleMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            ?.setIcon(Functions.bitmapFromVector(applicationContext,
                R.drawable.charging_point_map_icon))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapsstyle))
    }

    private fun setChargingPointRvAdapter() {
        val chargingList: ArrayList<ChargingPointITem> = ArrayList()
        chargingList.add(ChargingPointITem("WeWork 247 Park",
            "13th floor, Bus Stop, 247 Park, Lal Bahadur Shastri..."))
        chargingList.add(ChargingPointITem("WeWork Goregaon",
            "113th floor, Bus Stop, 247 Park, Lal Bahadur Shastri..."))
        chargingList.add(ChargingPointITem("WeWork Vijay Diamond",
            "13th floor, Bus Stop, 247 Park, Lal Bahadur Shastri..."))
        chargingList.add(ChargingPointITem("WeWork Chromium",
            "13th floor, Bus Stop, 247 Park, Lal Bahadur Shastri..."))
        val chargingPointAdapter = ChargingPointRvAdapter(this, chargingList)
        binding.rvChargingPoint.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvChargingPoint.adapter = chargingPointAdapter
        chargingPointAdapter.setOnItemClickListener(object :
            ChargingPointRvAdapter.OnItemClickListener {
            override fun onItemClick() {
                binding.rvChargingPoint.hide()
                binding.rlChargingPointMap.show()
                binding.llDirectionChargingPoint.show()
                binding.llDirectionChargingPoint.setOnClickListener {
                    val intent = Intent(mContext, ChargingPointDirectionActivity::class.java)
                    startActivity(intent)
                }
            }

        })
    }
}