package com.example.vida.ui.quickcontrol

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.R
import com.example.vida.databinding.ActivityMapNavigationBinding
import com.example.vida.databinding.NavigationDistanceBottomSheetBinding
import com.example.vida.databinding.PopupLayoutBinding
import com.example.vida.ui.dashboard.adapter.Stories
import com.example.vida.ui.dashboard.adapter.StoriesAdapter
import com.example.vida.utils.extension.Functions
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog


class MapNavigationActivity : AppCompatActivity(), OnMapReadyCallback {

    private var isChargingShow: Boolean = false
    private lateinit var mContext: AppCompatActivity
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapNavigationBinding
    private lateinit var popUpWindow: PopupWindow
    private var context: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        mContext = this
        getBundleData()
        initializeView()
        setUpGoogleMap()

    }

    private fun getBundleData() {
        isChargingShow = mContext.intent.getBooleanExtra("showChargingView", false)
    }

    private fun initializeView() {
        setUpStoriesAdapter()
        setPopupWindow()
        if (isChargingShow) {
            binding.llScooterChargingStation.show()
        } else {
            binding.llScooterChargingStation.hide()
        }
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.rlDots.setOnClickListener {
            binding.etSearch.hint = "Where to ?"

            popUpWindow.showAsDropDown(
                it,
                -300,
                10,
            )
        }
        binding.etSearch.setOnClickListener {
            if (binding.etSearch.hint == "Where to ?") {
                startActivity(
                    Intent(
                        this@MapNavigationActivity,
                        MapNavigationSetActivity::class.java
                    )
                )
            }
        }

        binding.llStart.setOnClickListener {
            binding.llScooterChargingStation.hide()
            binding.rlSearch.hide()
            binding.rlDots.hide()
            showBottomSheetDialog()
        }
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
            ?.setIcon(
                Functions.bitmapFromVector(
                    applicationContext,
                    R.drawable.ic_map_scooter
                )
            )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this,
                R.raw.mapsstyle
            )
        )
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        val bindingBottom = NavigationDistanceBottomSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bindingBottom.root)

        bindingBottom.llStartNavigation.setOnClickListener {
            startActivity(Intent(this@MapNavigationActivity, ExitNavigationActivity::class.java))
        }

        bottomSheetDialog.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
            binding.llScooterChargingStation.show()
            binding.rlSearch.show()
            binding.rlDots.show()
        }

        bottomSheetDialog.show()
    }

    private fun setPopupWindow() {
        var bindingPopUp: PopupLayoutBinding? = null
        val inflater: LayoutInflater =
            applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        bindingPopUp = PopupLayoutBinding.inflate(inflater)
        val view: View = bindingPopUp.root
        popUpWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        bindingPopUp.tvPopShareLocation.setOnClickListener {
            Functions.shareIntent(this)
            popUpWindow.dismiss()
        }
        bindingPopUp.tvPopRemote.setOnClickListener {
            val intent = Intent(this, RemoteImmobilizationActivity::class.java)
            intent.putExtra("quickControlValue", "RemoteImmobilization")
            startActivity(intent)
            popUpWindow.dismiss()
        }
    }

    private fun setUpStoriesAdapter() {
        val stories: ArrayList<Stories> = ArrayList()
        stories.add(Stories(R.drawable.ic_pump, "Find Charger"))
        stories.add(Stories(R.drawable.ic_home, "Home"))
        stories.add(Stories(R.drawable.ic_camera_record, "Restaurants"))
        val storiesAdapter = context?.let { StoriesAdapter(it, stories) }
        binding.rvStories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvStories.adapter = storiesAdapter
    }

}