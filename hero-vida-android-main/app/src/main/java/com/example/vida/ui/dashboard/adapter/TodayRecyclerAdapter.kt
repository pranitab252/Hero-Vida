package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vida.databinding.NavigationSearchResultItemLayoutBinding
import com.example.vida.databinding.RvTodayItemBinding
import com.example.vida.ui.dashboard.L1AlertActivity
import com.example.vida.ui.dashboard.adapter.TodayRecyclerAdapter.TodayRecyclerViewHolder

class TodayRecyclerAdapter(
    private val context: Context,
    private val todayNotificationList: List<TodayNotification>
) : RecyclerView.Adapter<TodayRecyclerViewHolder>() {
    inner class TodayRecyclerViewHolder(rvTodayItemBinding: RvTodayItemBinding) :
        RecyclerView.ViewHolder(rvTodayItemBinding.root) {
        var binding: RvTodayItemBinding? = null

        init {
            binding = rvTodayItemBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayRecyclerViewHolder {
        return TodayRecyclerViewHolder(
            RvTodayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TodayRecyclerViewHolder, position: Int) {
        val notification = todayNotificationList[position]
        Glide.with(context).load(notification.todayNotificationImage)
            .into(holder.binding!!.contactPhoto)
        holder.binding!!.tvNotificationHeading.text = notification.todayNotificationHeading
        holder.binding!!.tvNotificationContent.text = notification.todayNotificationContent
        holder.binding!!.tvTime.text = notification.todayNotificationTime
        holder.binding!!.root.setOnClickListener {
            val intent = Intent(context, L1AlertActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return todayNotificationList.size
    }
}