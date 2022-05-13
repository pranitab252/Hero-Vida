package com.example.vida.ui.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.vida.R
import com.example.vida.ui.intro.IntroItem

class OnboardingAdapter(var mContext: Context, var mListScreen: List<IntroItem>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen: View = inflater.inflate(R.layout.item_onboarding, null)

        val imgSlide = layoutScreen.findViewById<ImageView>(R.id.img_boading)
        val description = layoutScreen.findViewById<TextView>(R.id.tx_boarding)
        val tvTitle = layoutScreen.findViewById<TextView>(R.id.tx_title)

        tvTitle.text = mListScreen[position].title
        description.text = mListScreen[position].description
        Glide.with(mContext).load(mListScreen[position].imgResId).into(imgSlide)

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