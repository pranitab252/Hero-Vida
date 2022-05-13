package com.example.vida.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.vida.R
import com.example.vida.common.ProgressItem
import com.example.vida.databinding.ActivityTripDetailBinding
import com.example.vida.network.WebService
import com.example.vida.ui.dashboard.adapter.TripItemAdapter
import com.example.vida.ui.dashboard.adapter.TripItemDetail
import com.example.vida.ui.model.TripDetailCustom
import com.example.vida.utils.extension.Constants
import com.example.vida.utils.extension.Utils
import com.example.vida.utils.extension.getLocationTextFromLatLngForTrips
import com.otc.alice.api.model.MyTrip
import com.otcengineering.otcble.core.utils.GpxParser
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

class TripDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTripDetailBinding
    private var progressItemList: ArrayList<ProgressItem>? = null
    private var mProgressItem: ProgressItem? = null
    private var tripItemAdapter: TripItemAdapter? = null
    private var tripId: Long = -1

    private var timerForL1Alerts: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        initDataInSeekBar()
        setTripViewPager()

        initLogic()
    }

    private fun initLogic() {
        lifecycleScope.launchWhenCreated {
            tripId = intent.getLongExtra(Constants.EXTRA_DATA, -1L)
            if (tripId != -1L) {
                callGetRouteApi()
                callGetRouteTripAnalysisApi()
            }
        }

        initL1Alerts()
    }

    private fun initializeView() {
        binding.sbTripDetail.thumb.mutate().alpha = 0

        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        binding.swipeRefresh.setOnRefreshListener {
            initLogic()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initDataInSeekBar() {
        progressItemList = ArrayList()

        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 35f
        mProgressItem!!.color = R.color.colorAvailable;
        progressItemList!!.add(mProgressItem!!)

        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 1f
        mProgressItem!!.color = R.color.colorWhite;
        progressItemList!!.add(mProgressItem!!)

        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 64f
        mProgressItem!!.color = R.color.colorWarning;
        progressItemList!!.add(mProgressItem!!)

        binding.sbTripDetail.initData(progressItemList)
        binding.sbTripDetail.invalidate()
    }

    private fun setTripViewPager() {
        // fill list screen
        val mList: MutableList<TripItemDetail> = ArrayList()
        mList.add(
            TripItemDetail(
                resources.getString(R.string.harsh_braking),
                resources.getString(R.string.you_are_applying_brake_toofrequently_try_and_reduce_braking_by_50)
            )
        )

        mList.add(
            TripItemDetail(
                resources.getString(R.string.harsh_braking),
                resources.getString(R.string.you_are_applying_brake_toofrequently_try_and_reduce_braking_by_50)
            )
        )

        mList.add(
            TripItemDetail(
                resources.getString(R.string.harsh_braking),
                resources.getString(R.string.you_are_applying_brake_toofrequently_try_and_reduce_braking_by_50),
            )
        )

        tripItemAdapter = TripItemAdapter(this, mList)
        binding.vpTrip.adapter = tripItemAdapter
        binding.vpTrip.currentItem = 1
        binding.tlTrip.setupWithViewPager(binding.vpTrip)
    }

    private fun updateTripDetailsOnUi(tripDetails: TripDetailCustom) {
        binding.tvHeaderDate.text = tripDetails.dateTime
        binding.txtDistance.text = "${tripDetails.distance} km"
        binding.txtDuration.text = "${tripDetails.minute} mins"

        "${tripDetails.drivingTechnique}".let {
            binding.tvRatingNumber.text = it.substring(0, 4)
        }

        //TODO: add for route points too
        binding.tvRoutePick.text = tripDetails.pickLocation
        binding.tvRouteDrop.text = tripDetails.dropLocation

        binding.ratingBar.rating = tripDetails.drivingTechnique

        updateConsumptionOnUi(tripDetails.consumption)
    }

    private fun updateTopSpeedOnUi(topSpeed: Int) {
        binding.txtTopSpeed.text = "$topSpeed"
        binding.txtTopSpeedKmph.isVisible = true
    }

    private fun getAvgSpeed(analysisList: List<MyTrip.TripAnalysis>): Int {
        Utils.printDevLog(mssg = "analysisList: $analysisList")
        val count = analysisList.size
        var sum = 0.toDouble()
        for (item in analysisList) {
            sum += item.avgSpeed
        }

        return (sum / count).roundToInt()

    }

    private fun getTopSpeed(analysisList: List<MyTrip.TripAnalysis>): Int {
        Utils.printDevLog(mssg = "analysisList: $analysisList")
        var max = 0.toDouble()
        for (item in analysisList) {
            if (item.avgSpeed > max)
                max = item.avgSpeed
        }

        return max.roundToInt()
    }


    private fun updateAvgSpeedOnUi(avgSpeed: Int) {
        binding.txtAvgSpeed.text = "$avgSpeed"
        binding.txtAvgSpeedKmph.isVisible = true
    }

    private fun updateConsumptionOnUi(consumption: Int) {
        binding.txtConsumption.text = "$consumption"
        binding.txtConsumptionKw.isVisible = true
    }


    private fun getDrivingScore(drivingTechnique: Int) = (drivingTechnique / 100F) * 5


    private suspend fun callGetRouteApi() {
        try {
            binding.swipeRefresh.isRefreshing = true

            val response = WebService.Trip(this).getRoute(tripId)

            Utils.printDevLog(
                mssg = "$response"
            )
            if (response.body() != null) {

                Utils.printDevLog(
                    mssg = "${response.body()}"
                )

                val route = MyTrip.Route.parseFrom(response.body()?.data?.value)
                Utils.printDevLog(mssg = "$route")
                Utils.printDevLog(mssg = "${route.allFields}")


                var routeLocations = callGetGpxFileApi(route.gpxFileId)

                if (routeLocations.isEmpty())
                    routeLocations = arrayOf("", "")

                //for test
//                routeLocations = arrayOf("Noble Hospital", "Holkar Stadium")

                updateTripDetailsOnUi(
                    TripDetailCustom(
                        id = route.id,
                        minute = route.duration,
                        dateTime = Utils.formatDateTime(inputDateTime = route.dateStart),
                        distance = route.distance.toInt(),
                        consumption = route.avgConsumption.toInt(),
                        drivingTechnique = getDrivingScore(route.drivingTechnique),
                        pickLocation = routeLocations[0],
                        dropLocation = routeLocations[1]
                    )
                )
            }

        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        } finally {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private suspend fun callGetRouteTripAnalysisApi() {
        try {


            //5668
            val response = WebService.Trip(this).getRouteTripAnalysis(tripId) //5671, 4055

            Utils.printDevLog(
                mssg = "$response"
            )
            if (response.body() != null) {

                Utils.printDevLog(
                    mssg = "${response.body()}"
                )

                val analysis = MyTrip.RouteTripAnalysis.parseFrom(response.body()?.data?.value)
                Utils.printDevLog(mssg = "$analysis")
                Utils.printDevLog(mssg = "${analysis.allFields}")


                //TODO: trip analysis object model?
//                val tripAnalysis = MyTrip.TripAnalysis.parseFrom(analysis.tripAnalysis)

                updateAvgSpeedOnUi(getAvgSpeed(analysis.tripAnalysisList))
                updateTopSpeedOnUi(getTopSpeed(analysis.tripAnalysisList))

            }

        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        }
    }


    private suspend fun callGetGpxFileApi(id: Long): Array<String> {
        var start: String? = ""
        var end: String? = ""
        try {

            val response = WebService.File(this).getFile(id.toString())

            Utils.printDevLog(
                mssg = "$response"
            )
            if (response.body() != null) {

                Utils.printDevLog(
                    mssg = "${response.body()}"
                )

                val fileStr = response.body()?.string()
                Utils.printDevLog(mssg = "fileStr: $fileStr")

//                    val contentType = response.body()?.contentType()
//                    Utils.printDevLog(mssg = "contentType: $contentType")


                val latlongs = GpxParser.getPoints(fileStr?.toByteArray())
                Utils.printDevLog(mssg = "latlongs: $latlongs")

                if (latlongs.isNotEmpty()) {
                    start = latlongs[0].getLocationTextFromLatLngForTrips(this)
                    end = latlongs[latlongs.size - 1].getLocationTextFromLatLngForTrips(this)
                    Utils.printDevLog(mssg = "start: $start")
                    Utils.printDevLog(mssg = "end: $end")
                }


                /*val latlng =
                                            GpxParser.getPoints(byteArrayOf(allRoutes.routesList.get(0).gpxFileId.toByte()))
//                        GpxParser.getPoints(byteArrayOf(52895.toByte()))
                val gpx = GpxParser.Gpx()
                val trackPoint = GpxParser.TrackPoint()
                trackPoint.setLat(latlng[0].latitude)
                trackPoint.setLon(latlng[0].longitude)
                val track = GpxParser.Track()
                track.setTrackPointList(listOf(trackPoint))
                gpx.setTrack(track)*/

                //                    Utils.printDevLog(mssg = "gpx: $gpx")


            }

        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")

        }


        return if (start != null && end != null) arrayOf(start, end) else arrayOf("", "")
    }

    private fun initL1Alerts() {
        timerForL1Alerts = Timer()

        timerForL1Alerts?.scheduleAtFixedRate(timerTask {
            lifecycleScope.launchWhenCreated {
                Utils.callGetLatestAlertsNotificationListApi(this@TripDetailActivity, 1)
                Utils.printDevLog(mssg = "scheduleAtFixedRate called")
            }
        }, 1000L * 5, 1000L * 60) //5 secs, 1 minute

    }

    override fun onStop() {
        super.onStop()
        timerForL1Alerts?.cancel()
    }
}