package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vida.R
import com.example.vida.databinding.ContentItemStoriesBinding

/**
 * Created by Shahzaib Sohaib
 */

class StoriesAdapter(
    private val context: Context,
    private val storiesList: List<Stories>
) :
    RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder>() {

    inner class StoriesViewHolder(onContentItemAddressesBinding: ContentItemStoriesBinding) :
        RecyclerView.ViewHolder(onContentItemAddressesBinding.root) {
        var contentItemStoriesBinding: ContentItemStoriesBinding? = null

        init {
            this.contentItemStoriesBinding = onContentItemAddressesBinding
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): StoriesViewHolder {
        return StoriesViewHolder(
            ContentItemStoriesBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup, false
            )
        )
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val onStoriesList = storiesList[position]

        Glide.with(context).load(onStoriesList.storyImage)
            .into(holder.contentItemStoriesBinding?.imgStories!!)
        holder.contentItemStoriesBinding?.tvstories?.text = onStoriesList.storyName
    }

    override fun getItemCount(): Int {
        return storiesList.size
    }
}