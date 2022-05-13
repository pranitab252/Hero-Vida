package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vida.databinding.ChargingPointRvLayoutItemBinding
import com.example.vida.ui.dashboard.adapter.ChargingPointRvAdapter.ChargingPointViewHolder

class ChargingPointRvAdapter(
    val context: Context,
    private val chargingList: List<ChargingPointITem>,
) : RecyclerView.Adapter<ChargingPointViewHolder>() {
    private lateinit var mOnItemClickListener: OnItemClickListener

    inner class ChargingPointViewHolder(chargingPointBinding: ChargingPointRvLayoutItemBinding) :
        RecyclerView.ViewHolder(chargingPointBinding.root) {
        var binding: ChargingPointRvLayoutItemBinding = chargingPointBinding
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        mOnItemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChargingPointViewHolder {
        return ChargingPointViewHolder(ChargingPointRvLayoutItemBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChargingPointViewHolder, position: Int) {
        val list = chargingList[position]
        holder.binding.tvChargingPointHeading.text = list.heading
        holder.binding.tvChargingPointDetail.text = list.detail
        holder.binding.root.setOnClickListener {
            mOnItemClickListener.onItemClick()
        }
    }

    override fun getItemCount(): Int {
        return chargingList.size
    }

    interface OnItemClickListener {
        fun onItemClick()
    }
}