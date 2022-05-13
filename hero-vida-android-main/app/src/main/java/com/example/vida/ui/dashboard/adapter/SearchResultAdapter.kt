package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vida.databinding.NavigationSearchResultItemLayoutBinding
import com.example.vida.ui.quickcontrol.MapNavigationActivity

class SearchResultAdapter(
    private val context: Context,
    private val searchList: List<SearchResult>
) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    inner class SearchResultViewHolder(navigationSearchResultItemLayoutBinding: NavigationSearchResultItemLayoutBinding) :
        RecyclerView.ViewHolder(navigationSearchResultItemLayoutBinding.root) {
        var contentItemSearchBinding: NavigationSearchResultItemLayoutBinding? = null

        init {
            contentItemSearchBinding = navigationSearchResultItemLayoutBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            NavigationSearchResultItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val onSearchList = searchList[position]
        holder.contentItemSearchBinding?.tvKilometerItem?.text = onSearchList.kiloMeter
        holder.contentItemSearchBinding?.tvHeadingItem?.text = onSearchList.heading
        holder.contentItemSearchBinding?.tvDetailItem?.text = onSearchList.detail
        holder.contentItemSearchBinding?.root?.setOnClickListener {
            val intent = Intent(context, MapNavigationActivity::class.java)
            intent.putExtra("showChargingView", true)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}