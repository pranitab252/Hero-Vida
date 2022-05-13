package com.example.vida.ui.quickcontrol.GeoFence

import android.content.Context
import android.os.Bundle
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.example.vida.R
import com.example.vida.databinding.GeofencescreentwoBinding


import com.example.vida.utils.extension.Functions
import com.example.vida.utils.extension.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class GeoFencelocnoneActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mContext: AppCompatActivity
    private lateinit var mMap: GoogleMap
    private lateinit var binding: GeofencescreentwoBinding
    //private lateinit var binding: GeoFencelocnonebinding
    private lateinit var popUpWindow: PopupWindow
    private var context: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GeofencescreentwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        mContext = this

        setUpGoogleMap()

    }
    /* private fun getBundleData() {
         isChargingShow = mContext.intent.getBooleanExtra("showChargingView", false)
     }

     private fun initializeView() {

         setPopupWindow()
         if (isChargingShow) {
             binding.llScooterChargingStation.show()
         } else {
             binding.llScooterChargingStation.hide()
         }
         binding.imgBackArrow.setOnClickListener {
             finish()
         }
        *//* binding.rlDots.setOnClickListener {
            binding.etSearch.hint = "Where to ?"

            popUpWindow.showAsDropDown(
                it,
                -300,
                10,
            )
        }*//*
        binding.etSearch.setOnClickListener {
            if (binding.etSearch.hint == "Where to ?") {
                startActivity(
                    Intent(
                        this@GeoFencelocnone,
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
    }*/

    private fun setUpGoogleMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
        val idr = LatLng(22.71, 75.85)
        mMap.addMarker(MarkerOptions().position(idr).title("Marker in Sydney"))
            ?.setIcon(
                Functions.bitmapFromVector(
                    applicationContext,
                    R.drawable.newmapperimagegeo
                )
            )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(idr))
        try {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapsstyle))
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")
        }
    }

    /*private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        val bindingBottom = NavigationDistanceBottomSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bindingBottom.root)

        bindingBottom.llStartNavigation.setOnClickListener {
            startActivity(Intent(this@GeoFencelocnone, ExitNavigationActivity::class.java))
        }

        bottomSheetDialog.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
            binding.llScooterChargingStation.show()
            binding.rlSearch.show()
            binding.rlDots.show()
        }

        bottomSheetDialog.show()
    }*/

   /* private fun setPopupWindow() {
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
    }*/
}
