package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vida.databinding.SubscriptionUpgradePlanLayoutItemBinding
import com.example.vida.ui.dashboard.adapter.ManageSubscriptionUpgradePlaneAdapter.*
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show

class ManageSubscriptionUpgradePlaneAdapter(
    private val context: Context,
    private val upgradePlanList: List<SubscriptionUpgradePlan>,
    val isShowPrice: Boolean,
) : RecyclerView.Adapter<SubscriptionUpgradePlaneViewHolder>() {
    private lateinit var mOnItemClickListener: OnItemClickListener

    inner class SubscriptionUpgradePlaneViewHolder(subscriptionUpgradeBinding: SubscriptionUpgradePlanLayoutItemBinding) :
        RecyclerView.ViewHolder(subscriptionUpgradeBinding.root) {
        var binding: SubscriptionUpgradePlanLayoutItemBinding = subscriptionUpgradeBinding

    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        mOnItemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SubscriptionUpgradePlaneViewHolder {
        return SubscriptionUpgradePlaneViewHolder(SubscriptionUpgradePlanLayoutItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SubscriptionUpgradePlaneViewHolder, position: Int) {
        val upgradePlan = upgradePlanList[position]
        if (isShowPrice) {
            holder.binding.llPrice.show()
        } else {
            if (position == 0) {
                holder.binding.llPrice.hide()
            }
        }
        holder.binding.tvPlan.text = upgradePlan.plan
        holder.binding.tvPlanDetail.text = upgradePlan.planDetail
        holder.binding.tvPlanPrice.text = "₹${upgradePlan.planPrice}"
        holder.binding.tvPlanBought.text = upgradePlan.planBought
        holder.binding.root.setOnClickListener {
            mOnItemClickListener.onItemClick()
        }
    }

    override fun getItemCount(): Int {
        return upgradePlanList.size
    }

    interface OnItemClickListener {
        fun onItemClick()
    }
}