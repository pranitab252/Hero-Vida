package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.vida.databinding.ScooterInformationVpLayoutItemBinding

class ScooterInformationVpAdapter(
    var context: Context,
    var scooterList: List<ScooterInformationVpItem>
) :
    PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ScooterInformationVpLayoutItemBinding
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ScooterInformationVpLayoutItemBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.tvRegistrationNumber.text = scooterList[position].registrationNumber
        binding.tvVinNumber.text = scooterList[position].vinNumber
        Glide.with(context).load(scooterList[position].scooterImg)
            .into(binding.ivScooter)
        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return scooterList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}