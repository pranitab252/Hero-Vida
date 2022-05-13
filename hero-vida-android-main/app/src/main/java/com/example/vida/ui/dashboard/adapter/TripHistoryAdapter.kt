package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vida.databinding.TripHistoryItemBinding
import com.example.vida.ui.dashboard.TripDetailActivity
import com.example.vida.ui.dashboard.adapter.TripHistoryAdapter.TripHistoryViewModel
import com.example.vida.utils.extension.Utils

//TODO: TripHistoryCustom -> TripHistory once Response Model resolved
//class TripHistoryAdapter(private val context: Context, private val list: List<TripHistory>) :
class TripHistoryAdapter(
    private val context: Context,
    private val list: List<TripHistoryCustom>,
    private var listener: OnTripHistoryItemClickListener
) :
    RecyclerView.Adapter<TripHistoryViewModel>() {

    inner class TripHistoryViewModel(tripHistoryItemBinding: TripHistoryItemBinding) :
        RecyclerView.ViewHolder(tripHistoryItemBinding.root) {
        var contentItemTripBinding: TripHistoryItemBinding? = null

        init {
            contentItemTripBinding = tripHistoryItemBinding

            contentItemTripBinding?.root?.setOnClickListener {
                Utils.printDevLog(mssg = "binding position: $position")
                Utils.printDevLog(mssg = "binding adapterposition: $adapterPosition")
                Utils.printDevLog(mssg = "binding absoluteAdapterPosition: $absoluteAdapterPosition")
                Utils.printDevLog(mssg = "binding absoluteAdapterPosition: $bindingAdapterPosition")
                Utils.printDevLog(mssg = "bindind onCLick: $position")
                listener.onTripClick(adapterPosition)
//                val position = bindingAdapterPosition
//                if (position != RecyclerView.NO_POSITION)
//                    listener.onTripClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripHistoryViewModel {
        return TripHistoryViewModel(
            TripHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TripHistoryViewModel, position: Int) {
        val tripList = list[position]
        //TODO: display as per UI once Response Model resolved
//        holder.contentItemTripBinding?.tvTripKm?.text = tripList.kiloMeter
        holder.contentItemTripBinding?.tvTripKm?.text = "${tripList.distance} km"
        holder.contentItemTripBinding?.tvTripMin?.text = "${tripList.minute} mins"
        holder.contentItemTripBinding?.tvPickLocation?.text = tripList.pickLocation
        holder.contentItemTripBinding?.tvDropLocation?.text = tripList.dropLocation
//        holder.contentItemTripBinding?.tvTripTime?.text = tripList.date
        holder.contentItemTripBinding?.tvTripTime?.text =
            tripList.dateStart //TODO: format here or require formatted.
//        holder.contentItemTripBinding?.tvPickLocation?.text = tripList.pickLocation
//        holder.contentItemTripBinding?.tvDropLocation?.text = tripList.dropLocation
        holder.contentItemTripBinding?.root?.setOnClickListener {
//            val intent = Intent(context, TripDetailActivity::class.java)
//            context.startActivity(intent)

            if (position != RecyclerView.NO_POSITION)
                listener.onTripClick(position)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnTripHistoryItemClickListener {
        fun onTripClick(position: Int)
    }

}