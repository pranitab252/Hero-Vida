package com.example.vida.utils.extension

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vida.R
import com.example.vida.network.WebService
import com.example.vida.ui.dashboard.L1AlertActivity
import com.google.android.gms.maps.model.LatLng
import com.otc.alice.api.model.General
import com.otc.alice.api.model.ProfileAndSettings
import com.otc.alice.api.model.Shared
import com.otc.alice.api.model.Welcome
import com.otcengineering.otcble.core.utils.UniqueDeviceID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private val LOG_TAG = Constants.TAG_DEV_LOG + this::class.java.simpleName

    fun printProminentScreenLog(mssg: String, txtView: TextView) {
        if (txtView.text.isEmpty())
            txtView.text = mssg
        else
            txtView.append("\n$mssg")
    }

    fun printDevLog(tag: String = LOG_TAG, mssg: String, txtView: TextView) {
        if (Constants.FLAG_DEV_LOGS) {
            txtView.append("$mssg\n\n")
            printDevLog(tag, mssg)
        }
    }

    fun printDevLog(tag: String = LOG_TAG, mssg: String) {
        if (Constants.FLAG_DEV_LOGS)
            Log.d(tag, "$mssg\n")
    }

    fun clearLogs(txtView: TextView) {
        txtView.text = ""
    }


    private suspend fun callOtcRegisterApi(ctx: Context): Boolean {
        val welcome = Welcome.UserRegistration.newBuilder()

        welcome.mobilePhoneNumber = Constants.TEST_MOBILE_PHONE
        welcome.username = Constants.TEST_USERNAME
        welcome.email = Constants.TEST_EMAIL
        welcome.password = Constants.TEST_PASSWORD

//        welcome.mobileIMEI = UniqueDeviceID.getUniqueId()
        welcome.mobileIMEI = Constants.TEST_UDID_IMEI

        try {
            val response = WebService.Welcome(ctx).registerUser(welcome.build())

            printDevLog(
                LOG_TAG,
                "response: $response"
            )
            if (response.body() != null) {

                printDevLog(
                    LOG_TAG,
                    "response.body(): ${response.body()}"
                )

                if (response.body()?.status == Shared.OTCStatus.SUCCESS)
                    return true
            }
        } catch (e: Exception) {
            printDevLog(LOG_TAG, "Error: " + e.message)
        }
        return false
    }

    private suspend fun callActivateUserApi(ctx: Context): Boolean {
        val welcome = Welcome.UserActivation.newBuilder()

        welcome.phoneNumber = Constants.TEST_MOBILE_PHONE
        welcome.verificationCode = "1688"

        try {
            val response = WebService.Welcome(ctx).activateUser(welcome.build())
            printDevLog(
                LOG_TAG,
                "response: $response"
            )

            if (response.body() != null) {

                printDevLog(
                    LOG_TAG,
                    "response.body(): ${response.body()}"
                )

                if (response.body()?.status == Shared.OTCStatus.SUCCESS)
                    return true
            }
        } catch (e: Exception) {
            printDevLog(LOG_TAG, "Error: " + e.message)
        }
        return false
    }

    /*suspend fun testActive2(ctx: Context): Boolean {

        val welcome = Welcome.ActivationPhone.newBuilder()

        welcome.setActivation("Activated")
//        welcome.verificationCode = "1688"
//        welcome.mobileIMEI = UniqueDeviceID.getUniqueId()
        try {
            val response = WebService.Welcome(ctx).passwordRecovery(welcome.build())

            printDevLog(
                LOG_TAG,
                "$response"
            )
            if (response.body() != null) {

                printDevLog(
                    LOG_TAG,
                    "${response.body()}"
                )

                if (response.body()?.statusValue == 81)
                    testOtcChangePhoneApi(ctx, reLogin = true)
                else {

                    val loginResponse =
                        Welcome.LoginResponse.parseFrom(response.body()?.data?.value)
                    printDevLog(
                        LOG_TAG,
                        "$loginResponse"
                    )


//                            if (response.body()?.statusValue == 81)
//                                testChangePhoneApi(reLogin = true)
//                            else {



                    return true
//                            }

                }
            }
        } catch (e: Exception) {
            printDevLog(LOG_TAG, "Error: " + e.message)
        }
        return false
    }*/

    suspend fun callOtcLoginApi(ctx: Context): Boolean {
        val welcome = Welcome.Login.newBuilder()

        welcome.phoneNumber = Constants.TEST_MOBILE_PHONE
        welcome.password = Constants.TEST_PASSWORD

//        welcome.mobileIMEI = Constants.TEST_UDID_IMEI
        welcome.mobileIMEI = UniqueDeviceID.getUniqueId()

        try {
            val response = WebService.Welcome(ctx).login(welcome.build())

            printDevLog(
                LOG_TAG,
                "response: $response"
            )
            if (response.body() != null) {

                printDevLog(
                    LOG_TAG,
                    "response.body(): ${response.body()}"
                )

                if (response.body()?.status == Shared.OTCStatus.NEW_MOBILE)
                    callOtcChangePhoneApi(ctx, reLogin = true)
                else {

                    val loginResponse =
                        Welcome.LoginResponse.parseFrom(response.body()?.data?.value)
                    printDevLog(
                        LOG_TAG,
                        "$loginResponse"
                    )

                    WebService.setAuthToken(
                        ctx,
                        loginResponse.apiToken
                    )

                    if (response.body()?.status == Shared.OTCStatus.SUCCESS)
                        return true

                }
            }
        } catch (e: Exception) {
            printDevLog(LOG_TAG, "Error: " + e.message)
        }
        return false
    }

    private suspend fun callOtcChangePhoneApi(ctx: Context, reLogin: Boolean) {
        try {
            val welcome = Welcome.ChangePhone.newBuilder()

            welcome.phoneNumber = Constants.TEST_MOBILE_PHONE
            welcome.password = Constants.TEST_PASSWORD
//            welcome.mobileIMEI = Constants.TEST_UDID_IMEI
            welcome.mobileIMEI = UniqueDeviceID.getUniqueId()

            val response = WebService.Welcome(ctx).changeMobile(welcome.build())

            printDevLog(LOG_TAG, "response: $response")

            if (response.body() != null) {
                printDevLog(
                    LOG_TAG,
                    "response.body(): ${response.body()}"
                )

                if (response.body()?.status == Shared.OTCStatus.SUCCESS && reLogin) {
                    callOtcLoginApi(ctx)
                }
            }

        } catch (e: Exception) {
            printDevLog(LOG_TAG, "Error: " + e.message)
        }

    }
    suspend fun ShareCurrentLocationApi(ctx: Context): LatLng? {
        try {
//            binding.swipeLayout.isRefreshing = true
            val response = WebService.Location(ctx).getVehicleLocation()
            printDevLog(mssg = "response: $response")

            if (response.body() != null) {
                printDevLog(
                    mssg =
                    "response.body(): ${response.body()}"
                )

                val point = General.Point.parseFrom(response.body()?.data?.value)

                printDevLog(
                    mssg =
                    "$point"
                )

                printDevLog(
                    mssg = "point.allFields:\n${point.allFields}"
                )

                return if (point.latitude != null && point.longitude != null)
                    LatLng(point.latitude, point.longitude)
                else
                    null
//                return LatLng(-34.0, 151.0)

            }

        } catch (e: Exception) {
            printDevLog(mssg = "Error:" + e.message)
        } finally {
//            binding.swipeLayout.isRefreshing = false
        }

        return null

    }
    suspend fun callGetVehicleLocationApi(ctx: Context): LatLng? {
        try {
//            binding.swipeLayout.isRefreshing = true
            val response = WebService.Location(ctx).getVehicleLocation()
            printDevLog(mssg = "response: $response")

            if (response.body() != null) {
                printDevLog(
                    mssg =
                    "response.body(): ${response.body()}"
                )

                val point = General.Point.parseFrom(response.body()?.data?.value)

                printDevLog(
                    mssg =
                    "$point"
                )

                printDevLog(
                    mssg = "point.allFields:\n${point.allFields}"
                )

                return if (point.latitude != null && point.longitude != null)
                    LatLng(point.latitude, point.longitude)
                else
                    null
//                return LatLng(-34.0, 151.0)

            }

        } catch (e: Exception) {
            printDevLog(mssg = "Error:" + e.message)
        } finally {
//            binding.swipeLayout.isRefreshing = false
        }

        return null

    }

    private suspend fun callGetVehicleStatusApi(ctx: Context) {
        try {
//            binding.swipeLayout.isRefreshing = true
            val response = WebService.Location(ctx).getVehicleStatus()
            printDevLog(mssg = "response: $response")

            if (response.body() != null) {
                printDevLog(
                    mssg =
                    "response.body(): ${response.body()}"
                )

                val vehicleStatus =
                    General.VehicleStatus.parseFrom(response.body()?.data?.value)

                printDevLog(
                    mssg =
                    "$vehicleStatus"
                )

                printDevLog(
                    mssg = "vehicleStatus.allFields:\n${vehicleStatus.allFields}"
                )

            }

        } catch (e: Exception) {
            printDevLog(mssg = "Error:" + e.message)
        } finally {
//            binding.swipeLayout.isRefreshing = false
        }

    }


    suspend fun callGetLatestAlertsNotificationListApi(ctx: Context, page: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val response = WebService.ProfileAndSettings(ctx).getNotificationList(page)

                printDevLog(
                    mssg = "response: $response"
                )
                if (response.body() != null) {

                    printDevLog(
                        mssg = "response.body(): ${response.body()}"
                    )

                    val userNotifications =
                        ProfileAndSettings.UserNotifications.parseFrom(response.body()?.data?.value)
                    printDevLog(mssg = "$userNotifications")
//                    Utils.printDevLog(mssg = "${userNotifications.allFields}")


                    var alertText = ""
                    var shouldHideAlert = false
                    var isCriticalAlert = false


                    /*val noti = userNotifications.notificationsList[0]
                    if (!noti.readed) {
                        alertText = getAlertTextForNotificationIfAny(ctx, noti)
                        isCriticalAlert = isCriticalAlert(noti)
                        shouldHideAlert = isAlertToBeHidden(noti)
                    }*/

//                    for (noti in userNotifications.notificationsList.subList(0, 10))
                    for (noti in userNotifications.notificationsList) {
                        if (!noti.isWithinThreshold())
                            break
                        if (!noti.readed) {
                            callReadNotificationApi(ctx, noti.id.toInt())

                            alertText = getAlertTextForNotificationIfAny(ctx, noti)
                            isCriticalAlert = isCriticalAlert(noti)
                            shouldHideAlert = isAlertToBeHidden(noti)

                            if (alertText.isNotEmpty())
                                break
                        }
                    }





                    if (alertText.isNotEmpty()) {
                        val intent = Intent(ctx, L1AlertActivity::class.java)
                        intent.putExtra(Constants.EXTRA_DATA, alertText)
                        intent.putExtra(Constants.EXTRA_BOOLEAN, isCriticalAlert)
                        ctx.startActivity(intent)
                        return@withContext true
                    }

                    if (shouldHideAlert) {
                        if ((ctx is AppCompatActivity))
                            ctx.finish()
                    }

                }

            } catch (e: Exception) {
                printDevLog(mssg = "Error: " + e.message)
            }
            return@withContext false
        }

    private fun isCriticalAlert(noti: ProfileAndSettings.UserNotification): Boolean {
        printDevLog(mssg = "isCriticalAlert: $noti")

        //TODO: also in case of fell-down
        return (noti.description.contains(Constants.ALERT_ACCIDENT_ON)
                ||
                noti.description.contains(Constants.ALERT_PANIC_ON))
    }

    private fun isAlertToBeHidden(noti: ProfileAndSettings.UserNotification): Boolean {
        printDevLog(mssg = "isAlertToBeHidden: $noti")
        var isAlertToBeHidden = false
        when {
            noti.description.contains(Constants.ALERT_BATTERY_REMOVAL_OFF) -> {
                isAlertToBeHidden = true
            }
            noti.description.contains(Constants.ALERT_ACCIDENT_OFF) -> {
                isAlertToBeHidden = true
            }
            noti.description.contains(Constants.ALERT_PANIC_OFF) -> {
                isAlertToBeHidden = true
            }
            noti.description.contains(Constants.ALERT_THEFT_OFF) -> {
                isAlertToBeHidden = true
            }
        }
        return isAlertToBeHidden
    }

    private fun getAlertTextForNotificationIfAny(
        ctx: Context,
        noti: ProfileAndSettings.UserNotification
    ): String {
        printDevLog(mssg = "getAlertTextForNotificationIfAny: $noti")

        var alertMessage = ""
        when {
            noti.description.contains(Constants.ALERT_BATTERY_REMOVAL_ON) -> {
                alertMessage = ctx.getString(R.string.alert_title_battery_removed)
            }
            noti.description.contains(Constants.ALERT_ACCIDENT_ON) -> {
                alertMessage = ctx.getString(R.string.alert_title_accident)
            }
            noti.description.contains(Constants.ALERT_PANIC_ON) -> {
                alertMessage = ctx.getString(R.string.alert_title_panic_alert)
            }
            noti.description.contains(Constants.ALERT_THEFT_ON) -> {
                alertMessage = ctx.getString(R.string.alert_title_intruder_noticed)
            }
        }
        return alertMessage

    }


    fun formatDateTime(
        inputFormat: String = Constants.DATE_TIME_FORMAT_DB,
        outputFormat: String = Constants.DATE_TIME_FORMAT_LOCAL,
        inputDateTime: String
    ): String {
        var formatted = inputDateTime
        try {
            formatted = SimpleDateFormat(
                outputFormat,
                Locale.getDefault()
            ).format(
                SimpleDateFormat(
                    inputFormat,
                    Locale.getDefault()
                ).parse(inputDateTime)
            )
        } catch (e: Exception) {
            printDevLog(mssg = "Error: ${e.message}")
        }

        return formatted
    }


    private suspend fun callReadNotificationApi(ctx: Context, id: Int) {
        try {
            val response = WebService.ProfileAndSettings(ctx).readNotification(id)

            printDevLog(mssg = "response: $response")
            if (response.body() != null) {
                printDevLog(mssg = "response.body(): ${response.body()}")
            }
        } catch (e: Exception) {
            printDevLog(mssg = "Error: " + e.message)

        }
    }

}