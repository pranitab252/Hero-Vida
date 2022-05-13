package com.example.vida.utils.extension

object Constants {
    //flags
    const val FLAG_DEV_LOGS = true //TODO: usually check 'false' before release
    const val FLAG_ONBOARDING_FLOW = false //TODO: usually check 'true' before release

    //api
    const val BASE_URL = "http://hero-dev.otc010.com/"

    //for testing
    const val TEST_MOBILE_PHONE = "+00000860987052406227" //7020431104
    const val TEST_MOBILE_PHONE2 = "+919827522482" //7020431104
    const val TEST_PASSWORD = "HeroConnect.6227"
    const val TEST_UDID_IMEI =
        "2945e82b1553872fbbdc553aba1f70aa42e728a7f3568bc69054f3c5f76c8c6f" //354076821848597
    const val TEST_VIN = "ABCDEFGHIJKL08169"
    const val TEST_SERIAL_NUMBER = "1860987052407227"
    const val TEST_EMAIL = "HERO-94@mailinator.com" // himanshu12345@mailinator.com
    const val TEST_USERNAME = "HERO-94" // ${mobilePhoneNumber}
    const val TEST_MAC_ADDRESS = "00:00:AB:D2:FC:20"
    const val TEST_LATITUDE = 28.031001766967773
    const val TEST_LONGITUDE = 77.4986572265625

    //    const val TEST_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoxLCJvYmpJZCI6NTU3NCwib2JqUGVybWlzc2lvbnMiOjQsImNyZWF0aW9uIjoxNjUwOTg0NTg0NTE2LCJleHBpcmF0aW9uIjoxNjUxMDI3Nzg0NTE2LCJzZWVkIjo1MzQ3MDl9.Ivd3apD7P-hJjqIQmry6BAqOZL-d_BlN--Qcl0HO7cE"
//    const val TEST_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoxLCJvYmpJZCI6NTYxOSwib2JqUGVybWlzc2lvbnMiOjAsImNyZWF0aW9uIjoxNjUxMjEzMzUxMzI3LCJleHBpcmF0aW9uIjoxNjUxMjU2NTUxMzI3LCJzZWVkIjo3MjQzMjB9.z1UNPTZ2LMimroG_hQ8TmtykzI4X9WsWZRmRSsRfTSI"
    const val TEST_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoxLCJvYmpJZCI6NTYyMCwib2JqUGVybWlzc2lvbnMiOjAsImNyZWF0aW9uIjoxNjUxMjEzODkxODIxLCJleHBpcmF0aW9uIjoxNjUxMjU3MDkxODIxLCJzZWVkIjo3MTEzMDl9.0wBGpJi1VEKm2BhJ-MQUN8BTaODGwPQqOMFCxpwptRI"


//                request.accidentAlert = true
//                request.batteryRemovalAlert = true
//                request.keylessAlerts = true
//                request.panicAlert = true
//                request.speedAlert = true
//                request.theftAlert = true
//                request.maintenanceAlerts = true
//                request.seatAlerts = true
//                request.fuelTheftAlert = true
//                request.remoteImmiAlerts = true
//                request.fallDownAlert = true
//                request.chargingStopAlert = true



    const val ALERT_BATTERY_REMOVAL_ON = "BatteryRemovalAlert_ON"
    const val ALERT_BATTERY_REMOVAL_OFF = "BatteryRemovalAlert_OFF"

    const val ALERT_ACCIDENT_ON = "AccidentAlert_ON"
    const val ALERT_ACCIDENT_OFF = "AccidentAlert_OFF"

    const val ALERT_PANIC_ON = "PanicAlert_ON"
    const val ALERT_PANIC_OFF = "PanicAlert_OFF"

    const val ALERT_THEFT_ON = "TheftAlert_ON"
    const val ALERT_THEFT_OFF = "TheftAlert_OFF"



    //Date & Time Formats
    const val DATE_FORMAT_DB = "yyyy-MM-dd" //2022-04-29
    const val TIME_FORMAT_DB = "HH:mm:ss" //03:15:48
    const val TIME_FORMAT_DB_WITH_MILLIS = "HH:mm:ss.SSSSSS" //08:58:56.703899
    const val DATE_TIME_FORMAT_DB = "$DATE_FORMAT_DB $TIME_FORMAT_DB" //2022-04-29 03:15:48
    const val DATE_TIME_FORMAT_DB_WITH_MILLIS = "$DATE_FORMAT_DB $TIME_FORMAT_DB_WITH_MILLIS" //2022-05-05 08:58:56.703899

    const val DATE_FORMAT_LOCAL = "dd MMM yyyy" //17 Apr 2022
    const val TIME_FORMAT_LOCAL = "h:mm aa" //9:30 am, 11:30 pm
    const val DATE_TIME_FORMAT_LOCAL =
        "$DATE_FORMAT_LOCAL, $TIME_FORMAT_LOCAL" //17 Apr 2022, 9:30 am


    //Extras
    const val TAG_DEV_LOG = "devlog"

    const val EXTRA_DATA = "EXTRA_DATA"
    const val EXTRA_BOOLEAN = "EXTRA_BOOLEAN"
    const val DEFAULT_LIST_PAGINATION_TRIGGER_THRESHOLD = 2
}