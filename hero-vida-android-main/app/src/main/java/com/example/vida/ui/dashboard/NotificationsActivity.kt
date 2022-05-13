package com.example.vida.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.R
import com.example.vida.databinding.ActivityNotificationsBinding
import com.example.vida.ui.dashboard.adapter.SearchResult
import com.example.vida.ui.dashboard.adapter.SearchResultAdapter
import com.example.vida.ui.dashboard.adapter.TodayNotification
import com.example.vida.ui.dashboard.adapter.TodayRecyclerAdapter

class NotificationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTodayNotificationAdapter()
        setUpThisWeekNotificationAdapter()
    }

    private fun setUpTodayNotificationAdapter() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        val todayNotification: ArrayList<TodayNotification> = ArrayList()
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_alerts_notification,
                "Low Charge Alert",
                "Your scooter battery is less then 25%. Please fill up the charge within 30 kms",
                "10:00 am"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_accepted_notification,
                "Accepted Return Request!",
                "Your scooter will be return back before 4pm by Rampradeep.",
                "10:00 am"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_invite_pending_notification,
                "Invite Pending!",
                "Please inform to child user and register with mobile number & accept the child invite.",
                "10:00 am"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_stolen_scooter_notification,
                "Stolen Scooter Alert",
                "Please inform to child user and register with mobile number & accept the child invite.",
                "10:00 am"
            )
        )


        val searchReachAdapter = TodayRecyclerAdapter(this, todayNotification)
        binding.rvToday.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvToday.adapter = searchReachAdapter
    }

    private fun setUpThisWeekNotificationAdapter() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        val todayNotification: ArrayList<TodayNotification> = ArrayList()
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_alerts_success,
                "Scooter Returned Successfully",
                "Scooter returned - Ram no longer has access to scooter",
                "27 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_profile,
                "Jeet Desai Commented",
                "Jeet Desai replied to your answer",
                "26 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_profile,
                "Jeet Desai Liked",
                "Jeet Desai liked your post",
                "26 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_alerts_notification,
                "Service Reminder",
                "Gentle remainder for your scooter service due on 3 Mar 2022.",
                "21 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_stolen_scooter_notification,
                "Geo Fence Alert",
                "Your scooter is moved out of set parameter. Please call the rider. ",
                "20 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_stolen_scooter_notification,
                "Low Charge Alert",
                "Your scooter battery is less then 7%. Please fill up the charge within 11 kms",
                "20 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_stolen_scooter_notification,
                "Intruder Noticed!",
                "Someone if trying to access your scooter. Ignore check if it was you, or please check.",
                "20 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_error_toast,
                "Speed Alert",
                "You have crossed your speed limit. Slow down.",
                "21 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_stolen_scooter_notification,
                "Immobilization Initiated!",
                "Your scooter will be refreined of ignition, once turned off",
                "21 Jan"
            )
        )
        todayNotification.add(
            TodayNotification(
                R.drawable.ic_stolen_scooter_notification,
                "Panic Alert",
                "We have sent a notification and current location to emergency contacts.",
                "21 Jan"
            )
        )


        val searchReachAdapter = TodayRecyclerAdapter(this, todayNotification)
        binding.rvThisWeek.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvThisWeek.adapter = searchReachAdapter
    }
}