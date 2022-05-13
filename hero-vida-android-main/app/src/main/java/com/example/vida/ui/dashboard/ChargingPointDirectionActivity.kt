package com.example.vida.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vida.R
import com.example.vida.databinding.ActivityChargingPointDirectionBinding
import com.example.vida.utils.extension.Functions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class ChargingPointDirectionActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mContext: AppCompatActivity
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityChargingPointDirectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingPointDirectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext=this
        initializeView()
        setUpGoogleMap()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener { finish() }
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
            ?.setIcon(Functions.bitmapFromVector(applicationContext, R.drawable.ic_map_scooter))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapsstyle))
    }
}