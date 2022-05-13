package com.example.vida.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vida.R
import com.example.vida.common.ProgressItem
import com.example.vida.databinding.ActivityLiveTripTrackBinding
import com.example.vida.databinding.LiveTripTrackingSheetBinding

import com.example.vida.utils.extension.Functions
import com.example.vida.utils.extension.Utils
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class LiveTripTrackActivity : AppCompatActivity(), OnMapReadyCallback {
    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var binding: ActivityLiveTripTrackBinding
    private lateinit var bottomBinding: LiveTripTrackingSheetBinding
    private var progressItemList: ArrayList<ProgressItem>? = null
    private var mProgressItem: ProgressItem? = null
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveTripTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomBinding = binding.includeInLiveTrip
        initializeView()
        setUpGoogleMap()
        initDataInSeekBar()
    }

    private fun initializeView() {
        bottomBinding.sbLiveTraking.thumb.mutate().alpha = 0
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        sheetBehavior = BottomSheetBehavior.from(bottomBinding.bottomShow)
        (sheetBehavior as BottomSheetBehavior).addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.vBg.hide()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.vBg.show()
                binding.vBg.alpha = slideOffset
            }

        })
    }

    private fun setUpGoogleMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initDataInSeekBar() {
        progressItemList = ArrayList()

        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 45f
        Log.i("Mainactivity", mProgressItem!!.progressItemPercentage.toString() + "")
        mProgressItem!!.color = R.color.colorAvailable;
        progressItemList!!.add(mProgressItem!!)

        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 35f
        mProgressItem!!.color = R.color.colorWarning;
        progressItemList!!.add(mProgressItem!!)

        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 20f
        mProgressItem!!.color = R.color.colorOTPErrror;
        progressItemList!!.add(mProgressItem!!)


        bottomBinding.sbLiveTraking.initData(progressItemList)
        bottomBinding.sbLiveTraking.invalidate()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
        val idr = LatLng(22.71, 75.85)
        mMap.addMarker(MarkerOptions().position(idr).title("Marker in Sydney"))
            ?.setIcon(Functions.bitmapFromVector(applicationContext, R.drawable.ic_map_scooter))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(idr))

        try {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapsstyle))
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")
        }

    }
}