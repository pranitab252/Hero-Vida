package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.vida.databinding.GarbageScooterLayoutItemBinding
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show

class GarbageScooterAdapter(
    var context: Context,
    var garbageScooterList: List<GarbageScooterItem>
) :
    PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: GarbageScooterLayoutItemBinding
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = GarbageScooterLayoutItemBinding.inflate(inflater, container, false)
        val view = binding.root
        if (position == 0) {
            binding.tvGabageScooterActive.show()
            binding.tvSetAsDefault.hide()
        } else {
            binding.tvGabageScooterActive.hide()
            binding.tvSetAsDefault.show()
        }
        binding.tvGabageScooterModel.text = garbageScooterList[position].scooterModel
        Glide.with(context).load(garbageScooterList[position].scooterImg)
            .into(binding.ivGarbageScooter)
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return garbageScooterList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}