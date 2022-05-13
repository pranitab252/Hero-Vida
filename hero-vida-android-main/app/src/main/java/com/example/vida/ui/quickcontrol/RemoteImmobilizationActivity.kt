package com.example.vida.ui.quickcontrol

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vida.R
import com.example.vida.databinding.ActivityRemoteImmobilizationBinding


import com.example.vida.utils.extension.*
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.timerTask


class RemoteImmobilizationActivity : AppCompatActivity(), OnMapReadyCallback {

    private var isImmobilized: Boolean = false
    private lateinit var quickValue: String
    private lateinit var mContext: AppCompatActivity
    private lateinit var mMap: GoogleMap
    private var mUserLocationCircle: Circle? = null
    private var mVehicleLocationMarker: Marker? = null
    private lateinit var binding: ActivityRemoteImmobilizationBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var isLocationAllowed = false
    private val locPermissionName = Manifest.permission.ACCESS_FINE_LOCATION
    private var userLatLng: LatLng = LatLng(0.0, 0.0)


    private var vehicleLatLng: LatLng = LatLng(0.0, 0.0)
    private var timerForL1Alerts: Timer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRemoteImmobilizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this
        getBundleData()
        initializeView()
        setUpGoogleMap()

        initL1Alerts()
    }

    private fun getBundleData() {
        isImmobilized = mContext.intent.getBooleanExtra("isImmobilized", false)
        quickValue = intent.getStringExtra("quickControlValue").toString()

    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.rlCurrentLocation.setOnClickListener {
            getUserLocation()
        }

        binding.swipeRefresh.isEnabled = false


        if (isImmobilized) {
            binding.tvConformImmobilization.text =
                mContext.getString(R.string.cancel_immobilization)
            clickImmobilization()
            binding.tvTitle.text = "Scooter Immobilized"
        } else {
            binding.tvConformImmobilization.text = mContext.getString(R.string.immobilize_scooter)
            binding.tvTitle.text = "Remote Immobilization"
        }

        when (quickValue) {
            "RemoteImmobilization" -> {
                clickImmobilization()
            }
            "ScooterLocation" -> {
                clickScooterLocation()
            }
            "LiveTrack" -> {
                clickTrackScooter()
            }
        }

        binding.tvConformImmobilization.setOnClickListener {
            /*lifecycleScope.launchWhenCreated {
                callPutImmobilizationApi(this@RemoteImmobilizationActivity, isImmobilized, true)
            }*/

            initiateImmobilizationOrCancellation()
        }
    }

    private fun setUpGoogleMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    //CameraUpdateFactory.newLatLngBounds to show entire bounds fit on-screen
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

//        mMap.uiSettings.isCompassEnabled = true

        mMap.setOnCameraMoveListener {
            mUserLocationCircle?.let {

//                it.radius = it.radius * 2
            }

        }

        updateVehicleLocationUiMap()
    }

    private fun clickImmobilization() {
        binding.tvTitle.text = "Remote Immobilization"
        binding.llImmobilization.show()
        binding.llShareSctooerLocation.hide()
        binding.llTrackMyScooter.hide()

//        binding.tvConformImmobilization.setOnClickListener {
        /*lifecycleScope.launchWhenCreated {
            callPutImmobilizationApi(this@RemoteImmobilizationActivity, isImmobilized, true)
        }*/

//            initiateImmobilizationOrCancellation()
//        }
    }

    private fun clickScooterLocation()  {
        binding.tvTitle.text = "Share Scooter Location"
        binding.llImmobilization.hide()
        binding.llShareSctooerLocation.show()
        binding.llTrackMyScooter.hide()

        binding.llCurrentLocation.setOnClickListener {
            binding.imgCurrentLocation.setImageDrawable(resources.getDrawable(R.drawable.ic_location_select))
            binding.llCurrentLocation.background =
                resources.getDrawable(R.drawable.share_location_selected_bg)
            binding.imgShareLiveLocation.setImageDrawable(resources.getDrawable(R.drawable.ic_live_un_select))
            binding.llShareLiveLocation.background =
                resources.getDrawable(R.drawable.share_location_bg)
            binding.llLocationShareSetting.visibility = View.GONE


            val json = (vehicleLatLng) // String instance holding the above json
            val latitide = json.latitude
            val longitude = json.longitude
            val latlong  = arrayListOf(latitide,longitude)








            val jsonString = "{\"lat\":\""+latitide+"\",\"long\":\""+longitude+"\"}"


            println("JSON string is: $jsonString")
            /*val toast = Toast.makeText(
                applicationContext,
                jsonString,
                Toast.LENGTH_SHORT
            )
            toast.show()*/



            //  val x = mapOf("lat"=>"21.1150807", "long" -> "79.0452961").toString()*/
            val encoder: Base64.Encoder = Base64.getEncoder()
            val encoded: String = encoder.encodeToString(jsonString.toString().encodeToByteArray())
            //  val encoded: String = encoder.encodeToString(jsonString.encodeToByteArray())


            //println("Encoded Data: $encoded")
            /*  val decoder: Base64.Decoder = Base64.getDecoder()
                val decoded = String(decoder.decode(encoded))
                println("Decoded Data: $decoded")
                val toast = Toast.makeText(
                    applicationContext,
                    jsonString,
                    Toast.LENGTH_SHORT
                )

                toast.show()*/


            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://vida--stage.herokuapp.com/vida/location?secret="+encoded);
            startActivity(Intent.createChooser(shareIntent,getString(R.string.login)))

           /* Functions.shareIntent(this)*/
        }
        binding.llShareLiveLocation.setOnClickListener {
            binding.imgShareLiveLocation.setImageDrawable(resources.getDrawable(R.drawable.ic_live_select))
            binding.llShareLiveLocation.background =
                resources.getDrawable(R.drawable.share_location_selected_bg)
            binding.imgCurrentLocation.setImageDrawable(resources.getDrawable(R.drawable.ic_location_un_select))
            binding.llCurrentLocation.background =
                resources.getDrawable(R.drawable.share_location_bg)
            binding.llLocationShareSetting.visibility = View.VISIBLE
        }
        binding.tvShareLiveLocation.setOnClickListener {
            Functions.shareIntent(this)
        }
    }

    private fun clickTrackScooter() {
        binding.tvTitle.text = "Live Tracking"
        binding.llImmobilization.hide()
        binding.llShareSctooerLocation.hide()
        binding.llTrackMyScooter.show()


        binding.llTrackNavigate.setOnClickListener {
            binding.llTrackMyScooter.hide()
            binding.llExitTrack.show()
        }

            binding.llTrackShare.setOnClickListener {
                /*val toast = Toast.makeText(
                    applicationContext,
                    userLatLng.toString(),
                    Toast.LENGTH_SHORT
                )
    toast.show()*/

                val json = (userLatLng) // String instance holding the above json
                val latitide = json.latitude
                val longitude = json.longitude
                val latlong  = arrayListOf(latitide,longitude)








                val jsonString = "{\"lat\":\""+latitide+"\",\"long\":\""+longitude+"\"}"


                println("JSON string is: $jsonString")
                /*val toast = Toast.makeText(
                    applicationContext,
                    jsonString,
                    Toast.LENGTH_SHORT
                )
                toast.show()*/



                //  val x = mapOf("lat"=>"21.1150807", "long" -> "79.0452961").toString()*/
                val encoder: Base64.Encoder = Base64.getEncoder()
                val encoded: String = encoder.encodeToString(jsonString.toString().encodeToByteArray())
              //  val encoded: String = encoder.encodeToString(jsonString.encodeToByteArray())


                //println("Encoded Data: $encoded")
                /*  val decoder: Base64.Decoder = Base64.getDecoder()
                    val decoded = String(decoder.decode(encoded))
                    println("Decoded Data: $decoded")
                    val toast = Toast.makeText(
                        applicationContext,
                        jsonString,
                        Toast.LENGTH_SHORT
                    )

                    toast.show()*/


                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://vida--stage.herokuapp.com/vida/location?secret="+encoded);
                startActivity(Intent.createChooser(shareIntent,getString(R.string.login)))
                //userLatLng = LatLng(lat, lng)




                /* fun shareIntent(context: Context) {
                     val sendIntent: Intent = Intent().apply {
                         action = Intent.ACTION_SEND
                         initForLocation()
                         putExtra(
                             Intent.EXTRA_TEXT,
                             "https://vidaprod.herokuapp.com/vida/location?secret=eyJsYXQiOiIyOC42NDQ4MDAiLCJsb25nIjoiNzcuMjE2NzIxIn0="
                         )
                         type = "text/plain"
                     }
                     context.startActivity(sendIntent)*/

            }
        binding.tvExit.setOnClickListener {
            finish()

            }

        getUserLocation()


    }


    //for location
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private lateinit var locationCallback: LocationCallback


//    private var isLocationAllowed = false
//    private val locPermissionName = Manifest.permission.ACCESS_FINE_LOCATION


    private fun updateVehicleLocationUiMap() {
        lifecycleScope.launchWhenCreated {
            val latlng: LatLng? = Utils.callGetVehicleLocationApi(this@RemoteImmobilizationActivity)
            Utils.printDevLog(mssg = "otc latlng: $latlng")

            latlng?.let {
                vehicleLatLng = it
                if (it.latitude != 0.0 && it.longitude != 0.0) {
                    Utils.printDevLog(mssg = "entered latlng let: $it")

                    updateVehicleLocationUiText(it.getLocationTextFromLatLng(this@RemoteImmobilizationActivity, true))


                    if (mVehicleLocationMarker != null)
                        mVehicleLocationMarker?.remove()

                    mVehicleLocationMarker =
                        mMap.addMarker(MarkerOptions().position(it).title("Scooter"))
                    mVehicleLocationMarker?.setIcon(
                        Functions.bitmapFromVector(
                            applicationContext,
                            R.drawable.ic_map_scooter
                        )
                    )

                    /*mMap.addPolyline(
                    PolylineOptions().add()
                )*/

//                mMap.moveCamera(CameraUpdateFactory.newLatLng(it))

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 20f), 2000, null)
                }
            }
        }
    }

    private fun updateVehicleLocationUiText(txt: String?) {
        binding.tvLocation.text = txt

        //TODO: update location ETA
//        binding.tvLocationEta.text = "$time ($distance)"
//        hideLoadingProgressbar()
    }

    private fun updateUserLocationUiMap() {
        Utils.printDevLog(mssg = "user latlng: $userLatLng")

        userLatLng.let {
            Utils.printDevLog(mssg = "entered latlng let: $it")

            val clrGreen = resources.getColor(R.color.colorSolidGreen, null)
            val clrGreenAlpha = resources.getColor(R.color.colorSolidGreenAlpha20, null)

            if (mUserLocationCircle != null)
                mUserLocationCircle?.remove()

            mUserLocationCircle = mMap.addCircle(
                CircleOptions()
                    .center(it)
                    .fillColor(clrGreen)
                    .strokeColor(clrGreenAlpha)
                    .strokeWidth(20f)
                    .radius(5.toDouble())
            )


            if (vehicleLatLng.latitude != 0.0 && vehicleLatLng.longitude != 0.0) {

                //TODO: check PolyUtil

                mMap.addPolyline(
                    PolylineOptions()
                        .color(clrGreen)
                        .add(it, vehicleLatLng)
                )
            }

            /*mMap.addMarker(MarkerOptions().position(it).title("You"))
                ?.setIcon(
                    Functions.bitmapFromVector(
                        applicationContext,
                        R.drawable.ic_map_scooter
                    )
                )*/

//            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 20f), 2000, null)
        }
    }


    private fun getUserLocation() {
        when {
            shouldShowRequestPermissionRationale(locPermissionName) -> {
                showToast(
                    "Permission Needed",
                    "Location access needed to auto-detect location",
                    "",
                    false
                )
            }

            else ->
                requestPermission.launch(locPermissionName)
        }


    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isLocationAllowed = it
            if (it) initForLocation()
            else
                showToast("Permission Needed", "Please provide location permission", "", false)
        }

    private fun initForLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


//        loadingDialog = showLoadingProgressbar()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {

                val loc = result.lastLocation

                val lat = loc.latitude
                val lng = loc.longitude

                userLatLng = LatLng(lat, lng)
                updateUserLocationUiMap()

//                hideLoadingProgressbar()
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                Utils.printDevLog(
                    mssg =
                    "onLocationAvailability: isLocationAvailable =  " + locationAvailability.isLocationAvailable
                )
            }
        }

        Handler(mainLooper).postDelayed({
//            hideLoadingProgressbar()
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }, 3000L)

        checkLocationSettings()
    }

    private fun checkLocationSettings() {

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }


        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener {
            Utils.printDevLog(
                mssg =
                "checkLocationSettings: locationSettingsStates ${it.locationSettingsStates}"
            )


//            locationSettingsResponse.locationSettingsStates.


            getLocationUpdates()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                Utils.printDevLog(mssg = "checkLocationSettings: is ResolvableApiException")
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {

                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    resolutionForResult.launch(intentSenderRequest)
                    return@addOnFailureListener

                } catch (sendEx: IntentSender.SendIntentException) {

                    //check
                }
            }

            //check else part

        }

    }

    private val resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            Utils.printDevLog(
                mssg = "resolutionForResult launcher: entered " +
                        "\nresultCode: ${activityResult.resultCode}"
            )
            if (activityResult.resultCode == RESULT_OK)
                getLocationUpdates()
            else {
                showToast("Location Settings", "Please check location settings", "", false)
            }
        }


    @SuppressLint("MissingPermission")
    private fun getLocationUpdates() {

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            mainLooper
        )

    }


    private fun initiateImmobilizationOrCancellation() {
        val intent = Intent(
            this@RemoteImmobilizationActivity,
            RemoteImmobilizationConfirmActivity::class.java
//            DashboardActivity::class.java
        )
        //TODO: maybe this if condition should not be there once 'Confirm' ready then also
        /*if (isImmobilized) {
            intent.putExtra("isImmobilized", isImmobilized)
        }*/

//        intent.putExtra("remoteMessage", isImmobilized) //TODO: remove once 'Confirm' ready
        intent.putExtra("isImmobilized", isImmobilized) //TODO: remove once 'Confirm' ready

        startActivity(intent)
    }


    private fun initL1Alerts() {
        timerForL1Alerts = Timer()

        timerForL1Alerts?.scheduleAtFixedRate(timerTask {
            lifecycleScope.launchWhenCreated {
                Utils.callGetLatestAlertsNotificationListApi(this@RemoteImmobilizationActivity, 1)
                Utils.printDevLog(mssg = "scheduleAtFixedRate called")
            }
        }, 1000L * 5, 1000L * 60) //5 secs, 1 minute

    }

    override fun onStop() {
        super.onStop()
        timerForL1Alerts?.cancel()
    }
}