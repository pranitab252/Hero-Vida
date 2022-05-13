package com.example.vida.network

import android.content.Context
import com.example.vida.utils.extension.Constants
import com.otc.alice.api.model.*
import com.otcengineering.otcble.NetworkSDK
import com.otcengineering.otcble.core.utils.UniqueDeviceID
import com.otcengineering.otcble.remote.*
import okhttp3.ResponseBody
import retrofit2.Response



class WebService {

    companion object {
        private val LOG_TAG = Constants.TAG_DEV_LOG + this::class.java.simpleName
        private const val BASE_URL = Constants.BASE_URL

        fun getAuthToken(ctx: Context): String {
            return NetworkSDK(ctx, BASE_URL).tokenSession.getAuthToken()
        }

        fun setAuthToken(ctx: Context, token: String?) {
            NetworkSDK(ctx, BASE_URL).tokenSession.setAuthToken(token)
        }

        fun getUniqueId() = UniqueDeviceID.getUniqueId()
    }


    /*


        private const val NO_OF_LOG_CHAR = 1000

    private val webservice: Webservice by lazy {
        val client = OkHttpClient.Builder()
        client.connectTimeout(45, TimeUnit.SECONDS)
        client.readTimeout(45, TimeUnit.SECONDS)
        client.writeTimeout(45, TimeUnit.SECONDS)
        /*client.addNetworkInterceptor {

        }*/

        client.addInterceptor(getHttpLoggingInterceptor())

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ProtoConverterFactory.create())
            .client(client.build())
            .build()
            .create(Webservice::class.java)
    }

    fun getHttpLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            if (message.length > NO_OF_LOG_CHAR) {
                for (noOfLogs in 0..message.length / NO_OF_LOG_CHAR) {
                    if (noOfLogs * NO_OF_LOG_CHAR + NO_OF_LOG_CHAR < message.length) {
                        // Do nothing
                        if (BuildConfig.DEBUG) {
                            Log.d(LOG_TAG, message.substring(noOfLogs * NO_OF_LOG_CHAR, noOfLogs * NO_OF_LOG_CHAR + NO_OF_LOG_CHAR))
                        }
//                             Log.d(TAG, message.substring(noOfLogs * NO_OF_LOG_CHAR, noOfLogs * NO_OF_LOG_CHAR + NO_OF_LOG_CHAR))
                    } else {
                        // Do nothing
                        if (BuildConfig.DEBUG) {
                            Log.d(LOG_TAG, message.substring(noOfLogs * NO_OF_LOG_CHAR, message.length))
                        }
//                             Log.d(TAG, message.substring(noOfLogs * NO_OF_LOG_CHAR, message.length))
                    }
                }
            } else {
                // Do nothing
                if (BuildConfig.DEBUG) {
                    Log.d(LOG_TAG, message)
                }
//                    Log.d(TAG, message)
            }
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }



    * */

    class Welcome(private val ctx: Context) : WelcomeApi {
        override suspend fun activateUser(request: com.otc.alice.api.model.Welcome.UserActivation): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.activateUser(request)
        }

        override suspend fun changeMobile(request: com.otc.alice.api.model.Welcome.ChangePhone): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.changeMobile(request)
        }

        override suspend fun getCountries(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.getCountries()
        }

        override suspend fun getTerms(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.getTerms()
        }

        override suspend fun getVehicleModel(request: com.otc.alice.api.model.Welcome.Model): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.getVehicleModel(request)
        }

        override suspend fun isEmailAvailable(request: com.otc.alice.api.model.Welcome.EmailVerification): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.isEmailAvailable(request)
        }

        override suspend fun isPhoneNumberAvailable(request: com.otc.alice.api.model.Welcome.PhoneNumberVerification): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.isPhoneNumberAvailable(
                request
            )
        }

        override suspend fun isTemporalPassword(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.isTemporalPassword()
        }

        override suspend fun isUsernameAvailable(request: com.otc.alice.api.model.Welcome.UsernameVerification): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.isUsernameAvailable(request)
        }

        override suspend fun login(request: com.otc.alice.api.model.Welcome.Login): Response<Shared.OTCResponse> {
//        return DataRepo.buildLoginRequest(request.phoneNumber, request.password)
            return NetworkSDK(ctx, BASE_URL).welcomeApi.login(request)


        }

        override suspend fun logout(request: com.otc.alice.api.model.Welcome.Logout): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.logout(request)
        }

        override suspend fun passwordRecovery(request: com.otc.alice.api.model.Welcome.PasswordRecovery): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.passwordRecovery(request)
        }

        override suspend fun refreshToken(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.refreshToken()
        }

        override suspend fun registerUser(request: com.otc.alice.api.model.Welcome.UserRegistration): Response<Shared.OTCResponse> {
            //        return DataRepo.buildLoginRequest(request.phoneNumber, request.password)
            return NetworkSDK(ctx, BASE_URL).welcomeApi.registerUser(request)
        }

        override suspend fun resendActivationSms(request: com.otc.alice.api.model.Welcome.SmsResend): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.resendActivationSms(request)
        }

        override suspend fun sendPushToken(request: com.otc.alice.api.model.Welcome.PushTokenRegistration): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.sendPushToken(request)
        }

        override suspend fun updatePassword(request: com.otc.alice.api.model.Welcome.PasswordUpdate): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.updatePassword(request)
        }

        override suspend fun updateUserProfile(request: General.UserProfile): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).welcomeApi.updateUserProfile(request)
        }
    }


    class Trip(private val ctx: Context) : TripApi {

        override suspend fun deleteRoute(id: Long): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).tripApi.deleteRoute(id)
        }

        override suspend fun getAllRoutes(page: Int): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).tripApi.getAllRoutes(page)
        }

        override suspend fun getLastRouteSummery(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).tripApi.getLastRouteSummery()
        }

        override suspend fun getRoute(id: Long): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).tripApi.getRoute(id)
        }

        override suspend fun getRouteTripAnalysis(id: Long): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).tripApi.getRouteTripAnalysis(id)
        }

        override suspend fun getRoutes(request: MyTrip.Routes): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).tripApi.getRoutes(request)
        }

        override suspend fun updateRoute(request: MyTrip.RouteUpdate): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).tripApi.updateRoute(request)
        }
    }

    class Dashboard(private val ctx: Context) : DashboardApi {
        override suspend fun knowYourBike(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).dashboardApi.knowYourBike()
        }
    }

    class Location(private val ctx: Context) : LocationApi {
        override suspend fun deleteKeyless(id: Long): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.deleteKeyless(id)
        }


        override suspend fun getGeofencing(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.getGeofencing()
        }

        override suspend fun getKeyless(page: Int): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.getKeyless(page)
        }

        override suspend fun getRemoteActionStatus(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.getRemoteActionStatus()
        }

        override suspend fun getVehicleLocation(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.getVehicleLocation()
        }

        override suspend fun getVehicleStatus(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.getVehicleStatus()
        }

        override suspend fun postKeyless(request: LocationAndSecurity.KeyLessCreation): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.postKeyless(request)
        }

        override suspend fun putGeofencing(request: LocationAndSecurity.Geofencing): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.putGeofencing(request)
        }

        override suspend fun putImmobilization(request: LocationAndSecurity.ImmobilizationAction): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.putImmobilization(request)
        }

        override suspend fun putKeylessUseToClose(id: Long): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.putKeylessUseToClose(id)
        }

        override suspend fun putKeylessUseToOpen(id: Long): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.putKeylessUseToOpen(id)
        }

        override suspend fun putSeatLock(request: LocationAndSecurity.SeatLockAction): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).locationApi.putSeatLock(request)
        }
    }


    class ProfileAndSettings(private val ctx: Context) : ProfileAndSettingsApi {
        override suspend fun createSecondaryProfile(profile: com.otc.alice.api.model.ProfileAndSettings.UserSecondaryProfile): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.createSecondaryProfile(profile)
        }

        override suspend fun deleteNotification(id: Long): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteSecondaryProfile(id: Int): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteUserImage(): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getNotificationList(page: Int): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.getNotificationList(page)
        }

        override suspend fun getNotificationSettings(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.getNotificationSettings()
        }

        override suspend fun getProfile(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.getProfile()
        }

        override suspend fun getProfileSettings(): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.getProfileSettings()
        }

        override suspend fun getProfileTerms(): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getSecondaryProfiles(page: Int): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getUnreadNotificationCount(): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun putNotificationSettings(notifications: com.otc.alice.api.model.ProfileAndSettings.NotificationsStatus): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.putNotificationSettings(notifications)
        }

        override suspend fun putProfile(profile: com.otc.alice.api.model.ProfileAndSettings.UserUpdateProfile): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.putProfile(profile)
        }

        override suspend fun putProfileSettings(settings: com.otc.alice.api.model.ProfileAndSettings.SettingsStatus): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun putProfileTerms(terms: General.TermAcceptance): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.putProfileTerms(terms)
        }

        override suspend fun putUserImage(image: com.otc.alice.api.model.ProfileAndSettings.UserImage): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun readNotification(id: Int): Response<Shared.OTCResponse> {
            return NetworkSDK(ctx, BASE_URL).profileApi.readNotification(id)
        }

        override suspend fun updateSecondaryProfile(profile: com.otc.alice.api.model.ProfileAndSettings.UserSecondaryProfile): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }
    }


    class File(private val ctx: Context) : FileApi {
        override suspend fun getFile(id: String): Response<ResponseBody> {
            return NetworkSDK(ctx, BASE_URL).fileApi.getFile(id)
        }
    }

    class Storage : StorageApi {
        override suspend fun deleteFile(request: Wallet.DeleteDoc): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getFiles(): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getManual(): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun uploadFile(request: Wallet.UploadDoc): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }
    }

    class Upload : UploadApi {
        override suspend fun uploadFile(request: FileProto.UploadPostFile): Response<Shared.OTCResponse> {
            TODO("Not yet implemented")
        }
    }
}