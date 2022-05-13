package com.example.vida.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vida.R
import com.example.vida.databinding.ActivityPingMyScooterMapBinding
import com.example.vida.utils.extension.Constants
import com.example.vida.utils.extension.Functions
import com.example.vida.utils.extension.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlin.concurrent.timerTask


class  PingMyScooterMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityPingMyScooterMapBinding

    private lateinit var mMap: GoogleMap
    private var mVehicleLocationMarker: Marker? = null

    private var timerForL1Alerts: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPingMyScooterMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        setUpGoogleMap()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        initL1Alerts()

    }


    private fun setUpGoogleMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    Constants.TEST_LATITUDE,
                    Constants.TEST_LONGITUDE
                ),
                10f
            ), 2000, null
        )

        try {
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapsstyle))
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")
        }


        updateVehicleLocationUiMap()
    }

    private fun updateVehicleLocationUiMap() {
        lifecycleScope.launchWhenCreated {
            val latlng: LatLng? = Utils.callGetVehicleLocationApi(this@PingMyScooterMapActivity)
            Utils.printDevLog(mssg = "otc latlng: $latlng")

            latlng?.let {
                if (it.latitude != 0.0 && it.longitude != 0.0) {
                    Utils.printDevLog(mssg = "entered latlng let: $it")

                    if (mVehicleLocationMarker != null)
                        mVehicleLocationMarker?.remove()

                    mVehicleLocationMarker =
                        mMap.addMarker(MarkerOptions().position(it).title("Scooter"))
                    mVehicleLocationMarker
                        ?.setIcon(
                            Functions.bitmapFromVector(
                                applicationContext,
                                R.drawable.ic_map_scooter
                            )
                        )

                    /*mMap.addPolyline(
                    PolylineOptions().add()
                )*/

//                mMap.moveCamera(CameraUpdateFactory.newLatLng(it))

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 18f), 2000, null)
                }
            }
        }
    }


    private fun initL1Alerts() {
        timerForL1Alerts = Timer()

        timerForL1Alerts?.scheduleAtFixedRate(timerTask {
            lifecycleScope.launchWhenCreated {
                Utils.callGetLatestAlertsNotificationListApi(this@PingMyScooterMapActivity, 1)
                Utils.printDevLog(mssg = "scheduleAtFixedRate called")
            }
        }, 1000L * 5, 1000L * 60) //5 secs, 1 minute

    }

    override fun onStop() {
        super.onStop()
        timerForL1Alerts?.cancel()
    }
}