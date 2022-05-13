package com.example.vida.ui.dashboard.adapter

data class TripHistoryCustom(
//    val kiloMeter: String,
    val id: Long,
    val minute: Int,
    val dateStart: String,
    val dateEnd: String,
    val distance: Int,
    val pickLocation: String,
    val dropLocation: String
)


/*

{
  "14": 15626,
  "16": 100,
  "18": 100,
  "19": 100,
  "20": 100,
  "21": 15,
  "22": 20,
  "type": "USER",
  "dateStart": "2022-04-04 06:51:49",
  "dateEnd": "2022-04-04 06:51:58",
  "distance": 5,
  "avgConsumption": 100,
  "gpxFileId": 34620,
  "drivingTechnique": 1
}

 */
