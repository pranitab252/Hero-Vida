package com.example.vida.ui.dashboard.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.R
import com.example.vida.common.ProgressItem
import com.example.vida.databinding.FragmentTripBinding
import com.example.vida.network.WebService
import com.example.vida.ui.dashboard.LiveTripTrackActivity
import com.example.vida.ui.dashboard.TripDetailActivity
import com.example.vida.ui.dashboard.adapter.*
import com.example.vida.ui.personaldetail.CustomSpinnerAdapter
import com.example.vida.ui.personaldetail.SpinnerItems
import com.example.vida.utils.extension.*
import com.otc.alice.api.model.General
import com.otc.alice.api.model.MyTrip
import com.otcengineering.otcble.core.utils.GpxParser


class TripFragment : Fragment(),
    TripHistoryAdapter.OnTripHistoryItemClickListener {
    private var _binding: FragmentTripBinding? = null
    private var progressItemList: ArrayList<ProgressItem>? = null
    private var mProgressItem: ProgressItem? = null
    private var tripItemAdapter: TripItemAdapter? = null
    private var trips = mutableListOf<MyTrip.Route>()

    private var currentPage: Int = 1
    private var totalPages: Int = 1
//    private var isPageLoading = false

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTripBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initializeView()
        setUpSpinnerAdapter()
        initDataInSeekBar()
        setTripViewPager()
//        setUpTripAdapter()

        initLogic()
        return root
    }

    private fun initializeView() {
        binding.seekBarDifferentColors.thumb.mutate().alpha = 0

        binding.llcurrentLocationTrack.setOnClickListener {
            activity?.navigate<LiveTripTrackActivity>()
        }

        binding.swipeRefresh.setOnRefreshListener {
            currentPage = 1
            totalPages = 1
            initLogic()
            _binding?.swipeRefresh?.isRefreshing = false
        }

        getSafeContextOrNull()?.let {
            val layoutManager = LinearLayoutManager(
                it
            )

            layoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.rvTripHisory.layoutManager = layoutManager


            /*binding.rvTripHisory.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        Utils.printDevLog(mssg = "onScrolled: entered")

                        if (dy > dx && dy > 0) {
                            Utils.printDevLog(mssg = "onScrolled: entered if condition")

                            val visibleItemCount = recyclerView.layoutManager?.childCount
                                ?: 0
                            val pastVisibleItem =
                                (recyclerView.layoutManager as LinearLayoutManager?)?.findFirstVisibleItemPosition()
                                    ?: 0
                            val total =
                                recyclerView.layoutManager?.itemCount ?: 0

                            Utils.printDevLog(mssg = "onScrolled: all params \n")

                            if (_binding?.swipeRefresh?.isRefreshing == false) {
                                Utils.printDevLog(mssg = "onScrolled: entered if !isRefreshing")

                                if (visibleItemCount + pastVisibleItem >= total - Constants.DEFAULT_LIST_PAGINATION_TRIGGER_THRESHOLD

                                    && pastVisibleItem > 0

                                ) {
                                    Utils.printDevLog(mssg = "onScrolled: entered 2nd if condition ")
                                    if (currentPage < totalPages) {

                                        Utils.printDevLog(
                                            mssg =
                                            "onScrolled: entered if (viewModel.currentPage <= viewModel.totalPages)"
                                        )

                                        currentPage++
                                        callGetAllRoutesApiWithPagination(true)
                                    }
                                }
                            }
                        }

                    }
                }
            )*/

            binding.scrollview.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY == ((v as NestedScrollView).getChildAt(0)
                        .getMeasuredHeight() - v.getMeasuredHeight())
                ) {

                    Utils.printDevLog(mssg = "onScrolled: entered")

//                    if (dy > dx && dy > 0) {
                    Utils.printDevLog(mssg = "onScrolled: entered if condition")

                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem =
                        layoutManager.findFirstVisibleItemPosition()
                    val total =
                        layoutManager.itemCount

                    Utils.printDevLog(mssg = "onScrolled: all params \n")

                    Utils.printDevLog(
                        mssg =
                        "visibleItemCount $visibleItemCount, pastVisibleItem $pastVisibleItem, total $total, swipeRefresh ${binding.swipeRefresh.isRefreshing}, currentPage $currentPage totalPages $totalPages"
                    )

                    if (!binding.swipeRefresh.isRefreshing) {
                        Utils.printDevLog(mssg = "onScrolled: entered if !isRefreshing")

                        if (visibleItemCount + pastVisibleItem >= total - Constants.DEFAULT_LIST_PAGINATION_TRIGGER_THRESHOLD

//                            && pastVisibleItem > 0

                        ) {
                            Utils.printDevLog(mssg = "onScrolled: entered 2nd if condition ")
                            if (currentPage < totalPages) {

                                Utils.printDevLog(
                                    mssg =
                                    "onScrolled: entered if (viewModel.currentPage <= viewModel.totalPages)"
                                )

                                currentPage++
                                callGetAllRoutesApiWithPagination(true)
                            }
                        }
                    }


                }


            }
        }


    }

    private fun initLogic() {
        callGetAllRoutesApiWithPagination(false)

        /*lifecycleScope.launchWhenCreated {
            callGetRoutesApi()
            callGetLastRouteSummaryApi()
            callGetRouteApi()
            callGetRouteTripAnalysisApi()
        }*/
    }

    private fun setUpSpinnerAdapter() {
        val bloodList = arrayListOf<SpinnerItems>()

        bloodList.add(SpinnerItems("All time", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("Last week", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("Last Month", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("February 2022", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("January 2022", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("December 2021", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("November 2021", "R.drawable.ic_profile_image"))

        getSafeContextOrNull()?.let {
            val customDropDownAdapter = CustomSpinnerAdapter(it, bloodList)
            binding.tripSpinner.adapter = customDropDownAdapter
        }


    }

    private fun initDataInSeekBar() {
        progressItemList = ArrayList()
        // red span
        // red span
        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 45f
        Log.i("Mainactivity", mProgressItem!!.progressItemPercentage.toString() + "")
        mProgressItem!!.color = R.color.colorAvailable;
        progressItemList!!.add(mProgressItem!!)
        // blue span
        // blue span
        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 35f
        mProgressItem!!.color = R.color.colorWarning;
        progressItemList!!.add(mProgressItem!!)
        // green span
        // green span
        mProgressItem = ProgressItem()
        mProgressItem!!.progressItemPercentage = 20f
        mProgressItem!!.color = R.color.colorOTPErrror;
        progressItemList!!.add(mProgressItem!!)


        binding.seekBarDifferentColors.initData(progressItemList)
        binding.seekBarDifferentColors.invalidate()
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

        getSafeContextOrNull()?.let {
            tripItemAdapter = TripItemAdapter(it, mList)
            binding.vpTrip.adapter = tripItemAdapter
            binding.vpTrip.currentItem = 1
            binding.tlTrip.setupWithViewPager(binding.vpTrip)
        }


    }

    private fun setUpTripAdapter() {
        val searchResult: ArrayList<TripHistory> = ArrayList()
        searchResult.add(
            TripHistory(
                "2.4 km",
                "26 mins",
                "17 Apr 2021, 1:30 PM",
                "Dwaraka, Gandhi Nagar, Mumbai",
                "SV Road, Andheri West"
            )
        )
        searchResult.add(
            TripHistory(
                "2.4 km",
                "26 mins",
                "17 Apr 2021, 1:30 PM",
                "Dwaraka, Gandhi Nagar, Mumbai",
                "SV Road, Andheri West"
            )
        )
        searchResult.add(
            TripHistory(
                "2.4 km",
                "26 mins",
                "17 Apr 2021, 1:30 PM",
                "Dwaraka, Gandhi Nagar, Mumbai",
                "SV Road, Andheri West"
            )
        )
        searchResult.add(
            TripHistory(
                "2.4 km",
                "26 mins",
                "17 Apr 2021, 1:30 PM",
                "Dwaraka, Gandhi Nagar, Mumbai",
                "SV Road, Andheri West"
            )
        )
        searchResult.add(
            TripHistory(
                "2.4 km",
                "26 mins",
                "17 Apr 2021, 1:30 PM",
                "Dwaraka, Gandhi Nagar, Mumbai",
                "SV Road, Andheri West"
            )
        )

        /*val tripHistoryAdapter = TripHistoryAdapter(requireContext(), searchResult)
        binding.rvTripHisory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTripHisory.adapter = tripHistoryAdapter*/
    }

    private fun updateTripsListOnUi(
        trips: MutableList<MyTrip.Route>,
        routesLocations: MutableList<Array<String>>
    ) {
        val customTrips = mutableListOf<TripHistoryCustom>()
        for ((i, route) in trips.withIndex()) {

            if (routesLocations.size <= i)
                routesLocations.add(arrayOf("", ""))

            if (routesLocations[i].isEmpty()) {
                routesLocations[i] = arrayOf("", "")
            }

            customTrips.add(
                TripHistoryCustom(
                    id = route.id,
                    minute = route.duration,
                    dateStart = Utils.formatDateTime(inputDateTime = route.dateStart),
                    dateEnd = Utils.formatDateTime(inputDateTime = route.dateEnd),
                    distance = route.distance.toInt(),
                    pickLocation = routesLocations[i][0],
                    dropLocation = routesLocations[i][0]
                )
            )
        }

        getSafeContextOrNull()?.let {
            val tripHistoryAdapter = TripHistoryAdapter(it, customTrips, this)
            binding.rvTripHisory.adapter = tripHistoryAdapter

            ViewCompat.setNestedScrollingEnabled(binding.rvTripHisory, false)
        }

    }


    private fun callGetAllRoutesApiWithPagination(isAfterPagination: Boolean) {
        lifecycleScope.launchWhenCreated {
            try {
                getSafeContextOrNull()?.let { ctx ->

//                isPageLoading = true
                    _binding?.swipeRefresh?.isRefreshing = true

                    //TODO: GetAllRoutesApi pagination
                    val response = WebService.Trip(ctx).getAllRoutes(currentPage)

                    Utils.printDevLog(
                        mssg = "$response"
                    )
                    if (response.body() != null) {

                        Utils.printDevLog(
                            mssg = "${response.body()}"
                        )

                        val allRoutes =
                            MyTrip.RoutesResponse.parseFrom(response.body()?.data?.value)

                        Utils.printDevLog(mssg = "$allRoutes")

                        totalPages = allRoutes.totalPages

//                    Utils.printDevLog(mssg = "${allRoutes.allFields}")


                        val listOfRoutesLocations = MutableList(allRoutes.routesCount) {
                            arrayOf("", "")
                        }

                        for ((i, trip) in allRoutes.routesList.withIndex()) {
                            listOfRoutesLocations[i] = callGetGpxFileApi(trip.gpxFileId)

                            //for test
                            /*if(i ==0) {
                                val latlong = LatLng(22.714196, 75.874533)
                                val latlong2 = LatLng(22.71724348722178, 75.9152880111583)
                                listOfRoutesLocations[i] = arrayOf(latlong.getLocationTextFromLatLngForTrips(ctx) ?: "aaa", latlong2.getLocationTextFromLatLngForTrips(ctx) ?: "bbb")
                            }*/
                        }

                        if (isAfterPagination) {
                            val a = ArrayList(trips)
                            a.addAll(allRoutes.routesList)
                            trips = a
//                            updateTripsListOnUi(trips)
                        } else {
                            trips = allRoutes.routesList
//                            updateTripsListOnUi(trips)
                        }

                        /*val listOfRoutesLocations = MutableList(trips.size) {
                            arrayOf("", "")
                        }

                        for ((i, trip) in trips.withIndex()) {
                            listOfRoutesLocations[i] = callGetGpxFileApi(trip.gpxFileId)
                        }*/

                        updateTripsListOnUi(trips, listOfRoutesLocations)
                        /*val latlng =
    //                        GpxParser.getPoints(byteArrayOf(allRoutes.routesList.get(0).gpxFileId.toByte()))
                            GpxParser.getPoints(byteArrayOf(52895.toByte()))
                        val gpx = GpxParser.Gpx()
                        val trackPoint = GpxParser.TrackPoint()
                        trackPoint.setLat(latlng[0].latitude)
                        trackPoint.setLon(latlng[0].longitude)
                        val track = GpxParser.Track()
                        track.setTrackPointList(listOf(trackPoint))
                        gpx.setTrack(track)*/

//                    Utils.printDevLog(mssg = "gpx: $gpx")

                    }
                }
            } catch (e: Exception) {
                Utils.printDevLog(mssg = "Error: $e")
            } finally {
//                isPageLoading = false
                _binding?.swipeRefresh?.isRefreshing = false
            }

        }
    }

    private suspend fun callGetRoutesApi() {
        try {
            getSafeContextOrNull()?.let {
                val request = MyTrip.Routes.newBuilder()
                request.page = 1
                request.routeType = General.RouteType.AUTOSAVED
                request.routeTypeValue = 1

                val response = WebService.Trip(it).getRoutes(request.build())

                Utils.printDevLog(
                    mssg = "$response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "${response.body()}"
                    )

                    val routes = MyTrip.RoutesResponse.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$routes")
                    Utils.printDevLog(mssg = "${routes.allFields}")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")

        }
    }

    private suspend fun callGetLastRouteSummaryApi() {
        try {
            getSafeContextOrNull()?.let {

                val response = WebService.Trip(it).getLastRouteSummery()

                Utils.printDevLog(
                    mssg = "$response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "${response.body()}"
                    )

                    val summary = MyTrip.LastRouteSummary.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$summary")
                    Utils.printDevLog(mssg = "${summary.allFields}")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")

        }
    }

    private suspend fun callGetRouteApi(): MutableList<MyTrip.Route>? {
        try {
            getSafeContextOrNull()?.let {

                val response = WebService.Trip(it).getRoute(5737) //5671 4055

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


                    return mutableListOf(route)
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")

        }

        return null
    }

    private suspend fun callGetRouteTripAnalysisApi() {
        try {
            getSafeContextOrNull()?.let {

                //5668
                val response = WebService.Trip(it).getRouteTripAnalysis(5737) //5671, 4055

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
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")

        }
    }

    private suspend fun callUpdateRouteApi() {
        try {
            getSafeContextOrNull()?.let {
                val request = MyTrip.RouteUpdate.newBuilder()
                request.title = "route title"
                request.routeId = 1001
                request.description = "route desc"

                val response = WebService.Trip(it).updateRoute(request.build())

                Utils.printDevLog(
                    mssg = "$response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "${response.body()}"
                    )

                    //TODO: check response type and get parser
                    val routes = MyTrip.RoutesResponse.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$routes")
                    Utils.printDevLog(mssg = "${routes.allFields}")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")

        }
    }

    private suspend fun callGetGpxFileApi(id: Long): Array<String> {
        var start: String? = ""
        var end: String? = ""
        try {
            getSafeContextOrNull()?.let { ctx ->
                val response = WebService.File(ctx).getFile(id.toString())

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
                        start = latlongs[0].getLocationTextFromLatLngForTrips(ctx)
                        end = latlongs[latlongs.size - 1].getLocationTextFromLatLngForTrips(ctx)
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
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: $e")

        }

        return if (start != null && end != null) arrayOf(start!!, end!!) else arrayOf("", "")
    }

    override fun onTripClick(position: Int) {
        Utils.printDevLog(mssg = "onTripClick: $position")
        val intent = Intent(activity, TripDetailActivity::class.java)

        intent.putExtra(Constants.EXTRA_DATA, trips[position].id)
        startActivity(intent)
    }

}