package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.vida.R

class TripItemAdapter(var mContext: Context, var mListScreen: List<TripItemDetail>) :
    PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen: View = inflater.inflate(R.layout.trip_item, null)
        val title = layoutScreen.findViewById<TextView>(R.id.tvTripTitle)
        val description = layoutScreen.findViewById<TextView>(R.id.tvTripContent)
        title.text = mListScreen[position].title
        description.text = mListScreen[position].content
        container.addView(layoutScreen)
        return layoutScreen
    }

    override fun getCount(): Int {
        return mListScreen.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}