package com.example.vida.ui.dashboard.fragment

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.vida.databinding.DashboardMoreBottomSheetBinding
import com.example.vida.databinding.FragmentHomeBinding
import com.example.vida.databinding.QuickControlBottomSheetBinding
import com.example.vida.ui.dashboard.adapter.Stories
import com.example.vida.ui.dashboard.adapter.StoriesAdapter
import com.example.vida.ui.model.VehicleDataRealTimeCustomModel
import com.example.vida.ui.quickcontrol.MapNavigationActivity
import com.example.vida.ui.quickcontrol.RemoteImmobilizationActivity
import com.example.vida.utils.extension.*
import com.example.vida.utils.extension.ble.BleUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.example.vida.R
import com.example.vida.network.WebService
import com.example.vida.ui.dashboard.*

import com.google.gson.Gson
import com.otc.alice.api.model.*
import com.otcengineering.otcble.core.utils.UniqueDeviceID

class HomeFragment : Fragment() {
    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private var isImmobilizedExist: Boolean = true
    private var isImmobilizedExistShow: Boolean = true
    private var isImmobilizedSuccess: Boolean = false
    private var isImmobilizedSuccessShow: Boolean = true
    private var isPinSetSuccess: Boolean = false
    private var _binding: FragmentHomeBinding? = null
    private var bottomBinding: DashboardMoreBottomSheetBinding? = null
    private var isQuickControlOpen: Boolean = false
    private var isKeyLessBikeOpen: Boolean = false
    private lateinit var animation: AnimationDrawable

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bottomBinding = binding.includeInHome

        getBundleData()
        initializeView()
        setUpStoriesAdapter()

        initLogic()

        return root
    }

    private fun getBundleData() {
        arguments?.getBoolean("remoteMessage", true).let {
            if (it != null) {
                isImmobilizedExist = it
            }
        }
        arguments?.getBoolean("isImmobilized", true).let {
            if (it != null) {
                isImmobilizedSuccess = it
            }
        }
        arguments?.getBoolean("setPinSuccess", false).let {
            if (it != null) {
                isPinSetSuccess = it
            }
        }
    }

    private fun initializeView() {
        binding.ivNotification.setOnClickListener {
            val intent = Intent(requireActivity(), NotificationsActivity::class.java)
            startActivity(intent)
        }
        binding.rlMore.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.imgOpenSheet.setOnClickListener {
            showSheet()
        }

        bottomBinding!!.rlNavigationSolid.setOnClickListener {
            activity?.navigate<MapNavigationActivity>()
        }
        bottomBinding!!.rlNavigation.setOnClickListener {
            activity?.navigate<MapNavigationActivity>()
        }
        bottomBinding!!.tvCustomise.setOnClickListener {
            val intent = Intent(requireActivity(), CustomWidgetActivity::class.java)
            startActivity(intent)
        }

        bottomBinding!!.rlKeySolid.setOnClickListener {
            bottomBinding!!.rlKeySolid.background =
                ResourcesCompat.getDrawable(resources, R.drawable.dashboard_menu_options_bg, null)
            bottomBinding!!.rlKeySolid.backgroundTintList =
                ColorStateList.valueOf(ResourcesCompat.getColor(resources,
                    R.color.colorWhite,
                    null))
            binding.ivScooter.setImageDrawable(ResourcesCompat.getDrawable(resources,
                R.drawable.key_less_1,
                null))
        }

        bottomBinding!!.rlMotorSolid.setOnClickListener {
            if (isKeyLessBikeOpen) {
                bottomBinding!!.rlMotorSolid.background =
                    ResourcesCompat.getDrawable(resources,
                        R.drawable.dashboard_menu_options_bg,
                        null)
                bottomBinding!!.rlMotorSolid.backgroundTintList =
                    ColorStateList.valueOf(ResourcesCompat.getColor(resources,
                        R.color.colorWhite10,
                        null))

                isKeyLessBikeOpen = false

                animation = AnimationDrawable()
                ResourcesCompat.getDrawable(resources, R.drawable.key_less_6, null)
                    ?.let { animation.addFrame(it, 500) }
                ResourcesCompat.getDrawable(resources, R.drawable.key_less_7, null)
                    ?.let { animation.addFrame(it, 500) }
                animation.isOneShot=true
                binding.ivScooter.setImageDrawable(animation)
                animation.start()
            } else {
                isKeyLessBikeOpen = true
                bottomBinding!!.rlMotorSolid.background =
                    ResourcesCompat.getDrawable(resources,
                        R.drawable.dashboard_menu_options_bg,
                        null)
                bottomBinding!!.rlMotorSolid.backgroundTintList =
                    ColorStateList.valueOf(ResourcesCompat.getColor(resources,
                        R.color.colorWhite,
                        null))

                animation = AnimationDrawable()
                ResourcesCompat.getDrawable(resources, R.drawable.key_less_2, null)
                    ?.let { animation.addFrame(it, 500) }
                ResourcesCompat.getDrawable(resources, R.drawable.key_less_3, null)
                    ?.let { animation.addFrame(it, 500) }
                ResourcesCompat.getDrawable(resources, R.drawable.key_less_4, null)
                    ?.let { animation.addFrame(it, 500) }
                ResourcesCompat.getDrawable(resources, R.drawable.key_less_3, null)
                    ?.let { animation.addFrame(it, 500) }
                animation.isOneShot=true
                binding.ivScooter.setImageDrawable(animation)
                animation.start()
            }
        }

        bottomBinding!!.rlChargingPoing.setOnClickListener {
            val intent = Intent(requireActivity(), ChargingPointActivity::class.java)
            startActivity(intent)
        }

        if (!isImmobilizedExist) {
            if (isImmobilizedExistShow) {
                activity?.showToast(
                    "Immobilization initiated",
                    "Your scooter will be refrained of \nignition, once turned off",
                    "View Details",
                    false
                )
                binding.rlImmobilizedEnable.show()
                binding.ivVidaDashboard.hide()
                binding.ivErrorImmobilized.show()
                isImmobilizedExistShow = false
            } else {
                binding.rlImmobilizedEnable.show()
                binding.ivVidaDashboard.hide()
                binding.ivErrorImmobilized.show()
            }
        } else {
            binding.rlImmobilizedEnable.hide()
            binding.ivErrorImmobilized.hide()
            binding.ivVidaDashboard.show()
        }


        if (isImmobilizedSuccess) {
            if (isImmobilizedSuccessShow) {
                activity?.showToastSuccess(
                    "Cancelled Immobilization",
                    "You can ride your scooter again",
                    false
                )
                isImmobilizedSuccessShow = false
            }
        }
        if (isPinSetSuccess) {
            activity?.showToastSuccess("Biometrics set successfully!", "", true)
        }

        binding.imgEditImmobilized.setOnClickListener {
            val intent = Intent(activity, RemoteImmobilizationActivity::class.java)
            intent.putExtra("isImmobilized", !isImmobilizedSuccess)
            startActivity(intent)
        }

        sheetBehavior = BottomSheetBehavior.from(bottomBinding!!.bottomShow)
        (sheetBehavior as BottomSheetBehavior<*>).addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        if (bottomBinding!!.llQuickControlOptions.visibility == View.VISIBLE) {
                            bottomBinding!!.imgMainSlide.hide()
                        }
                        _binding?.swipeRefresh?.isEnabled = false
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomBinding!!.llButtonsSolid.show()
                        bottomBinding!!.rlScooterMoreDetail.hide()
                        bottomBinding!!.imgMainSlide.show()

                        _binding?.swipeRefresh?.isEnabled = true
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset in 0.0..1.0) {
                    bottomBinding!!.llButtonsSolid.hide()
                    bottomBinding!!.rlScooterMoreDetail.show()
                }
            }

        })


        bottomBinding!!.rlMoreSolid.setOnClickListener {
            if (sheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                if (bottomBinding!!.llQuickControlOptions.visibility == View.GONE) {
                    bottomBinding!!.llQuickControlOptions.show()
                    bottomBinding!!.rlMore.background =
                        ResourcesCompat.getDrawable(resources,
                            R.drawable.dashboard_menu_options_bg,
                            null)
                    bottomBinding!!.rlMore.backgroundTintList =
                        ColorStateList.valueOf(ResourcesCompat.getColor(resources,
                            R.color.colorRegisterButtonPrimary,
                            null))
                    isQuickControlOpen = true
                }
            } else {
                sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

        }


        bottomBinding!!.rlMore.setOnClickListener {
            if (isQuickControlOpen) {
                bottomBinding!!.llQuickControlOptions.hide()
                bottomBinding!!.imgMainSlide.show()
                bottomBinding!!.rlMore.background =
                    resources.getDrawable(R.drawable.dashboard_menu_options_bg)
                bottomBinding!!.rlMore.backgroundTintList = null
                isQuickControlOpen = false
            } else {
                bottomBinding!!.llQuickControlOptions.show()
                bottomBinding!!.imgMainSlide.hide()
                bottomBinding!!.rlMore.background =
                    resources.getDrawable(R.drawable.dashboard_menu_options_bg)
                bottomBinding!!.rlMore.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.colorRegisterButtonPrimary))
                isQuickControlOpen = true
            }

        }
        bottomBinding!!.rlRemoteImmobilization.setOnClickListener {
            sentIntent("RemoteImmobilization")
        }
        bottomBinding!!.rlShareScooterLocation.setOnClickListener {
            sentIntent("ScooterLocation")
        }
        bottomBinding!!.rlLiveTrack.setOnClickListener {
            sentIntent("LiveTrack")
        }
        bottomBinding!!.rlPingDashboard.setOnClickListener {
//            val intent = Intent(requireActivity(), PingMyScooterActivity::class.java) //TODO: only until ready
            val intent = Intent(requireActivity(), PingMyScooterMapActivity::class.java)
            startActivity(intent)
        }


        binding.swipeRefresh.setOnRefreshListener {
            initLogic()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpStoriesAdapter() {
        val stories: ArrayList<Stories> = ArrayList()
        stories.add(Stories(R.drawable.ic_profile, "Hi, Rahul"))
        stories.add(Stories(R.drawable.ic_offers, "Offers"))
        stories.add(Stories(R.drawable.ic_stories, "Stories"))
        stories.add(Stories(R.drawable.ic_looks, "Looks"))
        stories.add(Stories(R.drawable.ic_earth_points, "Earth Points"))
        val storiesAdapter = StoriesAdapter(requireContext(), stories)
        /*binding.rvStories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvStories.adapter = storiesAdapter*/
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = context?.let { BottomSheetDialog(it, R.style.SheetDialog) }
        val binding = QuickControlBottomSheetBinding.inflate(layoutInflater)
        bottomSheetDialog?.setContentView(binding.root)

        binding.rlRemoteImmobilization.setOnClickListener {
            sentIntent("RemoteImmobilization")
        }
        binding.rlShareScooterLocation.setOnClickListener {
            sentIntent("ScooterLocation")
        }
        binding.rlLiveTrack.setOnClickListener {
            sentIntent("LiveTrack")
        }

        bottomSheetDialog?.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
        }

        bottomSheetDialog?.show()
    }

    private fun showSheet() {
        val bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        val binding = DashboardMoreBottomSheetBinding.inflate(layoutInflater)
        bottomSheetDialog?.setContentView(binding.root)

        bottomSheetDialog?.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
        }

        bottomSheetDialog?.show()
    }


    private fun sentIntent(value: String) {
        val intent = Intent(activity, RemoteImmobilizationActivity::class.java)
        intent.putExtra("quickControlValue", value)

        //TODO: check isImmobilized value
//        intent.putExtra("isImmobilized", !isImmobilizedSuccess)
        intent.putExtra("isImmobilized", !isImmobilizedExist)

        startActivity(intent)
    }


    private fun initLogic() {
        getSafeContextOrNull()?.let {
            lifecycleScope.launchWhenCreated {

                _binding?.swipeRefresh?.isRefreshing = true
                var isAuthorized = callGetRemoteActionStatusApi(it, showRefresh = false)
                if (!isAuthorized)
                    isAuthorized = Utils.callOtcLoginApi(it)

                if (isAuthorized) {
                    callDashboardKnowYourBikeApi(it, showRefresh = false)
                    callGetRemoteActionStatusApi(it, showRefresh = false)
                    callBleUtilsFromSdk(it, showRefresh = false)
//                    callPutImmobilizationApi(it, true, showRefresh = false)
//                    callPutSeatLockApi(it, true, showRefresh = false)
//                    callPutImmobilizationApi(it, false, showRefresh = false)
//                    callPutSeatLockApi(it, false, showRefresh = false)

//                    callGetVehicleModelApi()
//                    callGetProfileApi()
//                    callGetProfileSettingsApi()
//                    callGetNotificationSettingsApi()
//                    callPushNotificationSettingsApi()
//                    callGetNotificationListApi(1)
//                    callReadNotificationApi(79626)

                }
                _binding?.swipeRefresh?.isRefreshing = false
            }
        }
    }


    private fun loadToken() {
        getSafeContextOrNull()?.let {
            WebService.setAuthToken(it, Constants.TEST_TOKEN)
        }
    }

    private fun updateBatteryPowerOnUI(socUserBms1: Double, socUserBms2: Double) {
        _binding?.tvBattery?.text = "${socUserBms1.toInt()}%\t ${socUserBms2.toInt()}%"
//        _binding?.tvBattery?.text = "${socUserBms1.toInt()}%\t${socUserBms2.toInt()}%"
        //TODO: show 2nd battery %.
        //TODO: update battery icon too?
        //TODO: confirm about 'Critical Low Charge Alert' (changes Battery icon and text color)

        //TODO: also update on bottom sheet.
        bottomBinding?.tvBattery?.text = "${socUserBms1.toInt()}%\t ${socUserBms2.toInt()}%"
    }

    private fun updateKilometerRangeOnUI(remainingRange: Int) {
        _binding?.tvDistance?.text = "$remainingRange km"

        //TODO: also update on bottom sheet.
        bottomBinding?.tvDistance?.text = "$remainingRange km"
    }

    private fun updateBluetoothConnectionStatusOnUI(statusValue: String) {
        _binding?.tvBluetooth?.text = "$statusValue"

        //TODO: also update on bottom sheet.
        bottomBinding?.tvBluetooth?.text = "$statusValue"
    }

    private fun updateLockStatusOnUI(statusValue: String, statusIconId: Int) {
        _binding?.tvLocked?.text = "$statusValue"
        _binding?.imgLocked?.setImageResource(statusIconId)

        bottomBinding?.tvLocked?.text = "$statusValue"
        bottomBinding?.imgLocked?.setImageResource(statusIconId)


        //TODO: check unlock status icon.
        //TODO: also update icon on lock button in UI.
        //TODO: also update on bottom sheet.
    }

    private fun updateImmobilizationStatusOnUI(status: Boolean) {
        _binding?.rlImmobilizedEnable?.isVisible = status
        //TODO: maybe more.
    }

    private fun callBleUtilsFromSdk(ctx: Context, showRefresh: Boolean) {
        if (showRefresh) _binding?.swipeRefresh?.isRefreshing = true
        updateBluetoothConnectionStatusOnUI(
            if (BleUtils.isConnected()) ctx.getString(R.string.tv_connected)
            else ctx.getString(R.string.tv_disconnected)
        )
        if (showRefresh) _binding?.swipeRefresh?.isRefreshing = false


        /*
        Utils.printDevDevLog(LOG_TAG, "BleSDK.isBluetoothOn: ${BleUtils.isBluetoothOn()}")

        Utils.printProminentScreenLog(
            "is Bluetooth On: ${BleUtils.isBluetoothOn()}",
            binding.includedProminentLogger.txtProminentLog
        )


        val status = BleUtils.getStatus()
        Utils.printDevDevLog(LOG_TAG, "BleSDK.getStatus: ${status.getEntries()}")

        Utils.printProminentScreenLog(
            "status: ${status.getEntries()}", binding.includedProminentLogger.txtProminentLog
        )

        Utils.printDevDevLog(LOG_TAG, "BleSDK.isConnected: ${BleUtils.isConnected()}")

        Utils.printProminentScreenLog(
            "BLE Connected:  ${BleUtils.isConnected()}",
            binding.includedProminentLogger.txtProminentLog
        )


        Utils.printDevDevLog(LOG_TAG, "BleSDK.getConnectedMAC: ${BleUtils.getConnectedMAC()}")

        Utils.printProminentScreenLog(
            "BLE connected MAC: ${BleUtils.getConnectedMAC()}",
            binding.includedProminentLogger.txtProminentLog
        )

        getSafeContext()?.apply {
            val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
//            bluetoothAdapter?.getRemoteDevice("deviceAddress")


            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                Utils.printDevDevLog(LOG_TAG, "paired Device name: ${device.name}")
                Utils.printDevDevLog(LOG_TAG, "paired Device mac: ${device.address}")
            }
        }

         */

    }

    private suspend fun callDashboardKnowYourBikeApi(ctx: Context, showRefresh: Boolean) {
        try {
            if (showRefresh) _binding?.swipeRefresh?.isRefreshing = true

            val response = WebService.Dashboard(ctx).knowYourBike()
            Utils.printDevLog(mssg = "response: $response")

            if (response.body() != null) {
                Utils.printDevLog(mssg = "response.body(): ${response.body()}")

                val knowYourBike =
                    DashboardAndStatus.KnowYourBike.parseFrom(response.body()?.data?.value)
                Utils.printDevLog(mssg = "$knowYourBike")


                val lastLocation = knowYourBike.lastLocation
                Utils.printDevLog(
                    mssg = "Last Location: $lastLocation"
                )

                val lastLocationTimestamp = knowYourBike.lastLocationTimestamp
                Utils.printDevLog(
                    mssg = "Last Location Timestamp: $lastLocationTimestamp"
                )

                //TODO: check knowYourBike.vehicleData parsing
                /*val vehicleData = knowYourBike.vehicleData
                Utils.addLog(
                    LOG_TAG,
                    "vehicleData: ${vehicleData}",
                    binding.includedLogger.txtLog
                )*/


                // TODO: standard VehicleDataRealTime Model in SDK ??
                val vehicleDataRT =
                    Gson().fromJson(
                        knowYourBike.vehicleDataRealTime,
                        VehicleDataRealTimeCustomModel::class.java
                    )

                Utils.printDevLog(
                    mssg = "$vehicleDataRT"
                )


                val vehicleDataTimestamp = knowYourBike.vehicleDataTimestamp
                Utils.printDevLog(
                    mssg =
                    "Vehicle Data Timestamp: $vehicleDataTimestamp"
                )

                updateBatteryPowerOnUI(vehicleDataRT.socUserBms1, vehicleDataRT.socUserBms2)
                updateKilometerRangeOnUI(vehicleDataRT.remainingRange)
            }


        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error:" + e.message)

        } finally {
            if (showRefresh) _binding?.swipeRefresh?.isRefreshing = false
        }
    }


    private suspend fun callGetRemoteActionStatusApi(ctx: Context, showRefresh: Boolean): Boolean {
        try {
            if (showRefresh) _binding?.swipeRefresh?.isRefreshing = true

            val response = WebService.Location(ctx).getRemoteActionStatus()
            Utils.printDevLog(mssg = "response: $response")

            if (response.body() != null) {
                Utils.printDevLog(mssg = "response.body(): ${response.body()}")

                val remoteActionsStatus =
                    LocationAndSecurity.RemoteActionsStatus.parseFrom(response.body()?.data?.value)
                Utils.printDevLog(
                    mssg = "remoteActionsStatus: $remoteActionsStatus"
                )

                if (remoteActionsStatus.seatLocked)
                    updateLockStatusOnUI(
                        ctx.getString(R.string.tv_locked), R.drawable.ic_locked
                    )
                else updateLockStatusOnUI(
                    ctx.getString(R.string.tv_unlocked), R.drawable.ic_unlocked
                )

                updateImmobilizationStatusOnUI(remoteActionsStatus.vehicleImmobilized)

                val allFields = remoteActionsStatus.allFields
                Utils.printDevLog(
                    mssg =
                    "remote status all Fields: $allFields"
                )

                if (response.body()?.statusValue == Shared.OTCStatus.SUCCESS_VALUE)
                    return true
            }


        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error:" + e.message)

        } finally {
            if (showRefresh) _binding?.swipeRefresh?.isRefreshing = false
        }

        return false
    }

    private suspend fun callPutImmobilizationApi(
        ctx: Context,
        immobilize: Boolean,
        showRefresh: Boolean
    ) {
        try {
            if (showRefresh) _binding?.swipeRefresh?.isRefreshing = true

            val welcome = LocationAndSecurity.ImmobilizationAction.newBuilder()
            welcome.immobilize = immobilize

            val response = WebService.Location(ctx).putImmobilization(welcome.build())
            Utils.printDevLog(mssg = "response: $response")

            if (response.body() != null) {
                Utils.printDevLog(mssg = "response.body(): ${response.body()}")

                val immobilizationAction =
                    LocationAndSecurity.ImmobilizationAction.parseFrom(response.body()?.data?.value)
                Utils.printDevLog(
                    mssg = "immobilizationAction: $immobilizationAction"
                )


                val allFields = immobilizationAction.allFields
                Utils.printDevLog(
                    mssg =
                    "immobilizationAction all Fields: $allFields"
                )

            }


        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error:" + e.message)

        } finally {
            if (showRefresh) _binding?.swipeRefresh?.isRefreshing = false
        }
    }

    private suspend fun callPutSeatLockApi(
        ctx: Context,
        lockStatus: Boolean,
        showRefresh: Boolean
    ) {
        try {
            if (showRefresh) _binding?.swipeRefresh?.isRefreshing = true

            val welcome = LocationAndSecurity.SeatLockAction.newBuilder()
            welcome.lock = lockStatus

            val response = WebService.Location(ctx).putSeatLock(welcome.build())
            Utils.printDevLog(mssg = "response: $response")

            if (response.body() != null) {
                Utils.printDevLog(mssg = "response.body(): ${response.body()}")


                val lockAction =
                    LocationAndSecurity.SeatLockAction.parseFrom(response.body()?.data?.value)
                Utils.printDevLog(
                    mssg = "lockAction: $lockAction"
                )


                val allFields = lockAction.allFields
                Utils.printDevLog(
                    mssg =
                    "lockAction all Fields: $allFields"
                )

            }


        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error:" + e.message)

        } finally {
            if (showRefresh) _binding?.swipeRefresh?.isRefreshing = false
        }
    }


    private suspend fun callGetProfileApi() {
        try {
            getSafeContextOrNull()?.let {
                val response = WebService.ProfileAndSettings(it).getProfile()

                Utils.printDevLog(
                    mssg = "response: $response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "response.body(): ${response.body()}"
                    )

                    val modelResponse =
                        ProfileAndSettings.UserProfileResponse.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$modelResponse")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        }
    }


    private suspend fun callGetProfileSettingsApi() {
        try {
            getSafeContextOrNull()?.let {
                val response = WebService.ProfileAndSettings(it).getProfileSettings()

                Utils.printDevLog(
                    mssg = "response: $response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "response.body(): ${response.body()}"
                    )

                    val modelResponse =
                        ProfileAndSettings.SettingsStatus.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$modelResponse")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        }
    }


    private suspend fun callGetNotificationSettingsApi() {
        try {
            getSafeContextOrNull()?.let {
                val response = WebService.ProfileAndSettings(it).getNotificationSettings()

                Utils.printDevLog(
                    mssg = "response: $response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "response.body(): ${response.body()}"
                    )

                    //TODO: check response type and get parser
                    val modelResponse =
                        ProfileAndSettings.UserProfileResponse.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$modelResponse")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        }
    }

    private suspend fun callGetVehicleModelApi() {
        try {
            getSafeContextOrNull()?.let {
                val request = Welcome.Model.newBuilder()
                request.vin = Constants.TEST_VIN

                val response = WebService.Welcome(it).getVehicleModel(request.build())

                Utils.printDevLog(
                    mssg = "response: $response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "response.body(): ${response.body()}"
                    )

                    val modelResponse =
                        Welcome.ModelResponse.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$modelResponse")
                    Utils.printDevLog(mssg = "mode all fields: ${modelResponse.allFields}")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        }
    }


    private suspend fun callPushNotificationSettingsApi() {
        try {
            getSafeContextOrNull()?.let {
                val request = ProfileAndSettings.NotificationsStatus.newBuilder()
//                request.setAccidentAlert()
//                request.setBatteryRemovalAlert()
//                request.setKeylessAlerts()
//                request.setPanicAlert()
//                request.setSpeedAlert()
//                request.setTheftAlert()
//                request.setMaintenanceAlerts()
//                request.setSeatAlerts()
//                request.setFuelTheftAlert()
//                request.setRemoteImmiAlerts()

                val response =
                    WebService.ProfileAndSettings(it).putNotificationSettings(request.build())

                Utils.printDevLog(
                    mssg = "response: $response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "response.body(): ${response.body()}"
                    )

                    //TODO: check response type and get parser
                    val modelResponse =
                        ProfileAndSettings.UserProfileResponse.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$modelResponse")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        }
    }


    private suspend fun callGetNotificationListApi(page: Int) {
        try {
            getSafeContextOrNull()?.let {
                val response = WebService.ProfileAndSettings(it).getNotificationList(page)

                Utils.printDevLog(
                    mssg = "response: $response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "response.body(): ${response.body()}"
                    )

                    val modelResponse =
                        ProfileAndSettings.UserNotifications.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$modelResponse")
                    Utils.printDevLog(mssg = "${modelResponse.allFields}")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        }
    }

    private suspend fun callReadNotificationApi(id: Int) {
        try {
            getSafeContextOrNull()?.let {
                val response = WebService.ProfileAndSettings(it).readNotification(id)

                Utils.printDevLog(
                    mssg = "response: $response"
                )
                if (response.body() != null) {

                    Utils.printDevLog(
                        mssg = "response.body(): ${response.body()}"
                    )

                    val modelResponse =
                        ProfileAndSettings.UserNotification.parseFrom(response.body()?.data?.value)
                    Utils.printDevLog(mssg = "$modelResponse")
                    Utils.printDevLog(mssg = "${modelResponse.allFields}")
                }
            }
        } catch (e: Exception) {
            Utils.printDevLog(mssg = "Error: " + e.message)

        }
    }

}