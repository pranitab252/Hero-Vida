package com.example.vida.ui.personaldetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.vida.R

class CustomSpinnerAdapter(val context: Context, var dataSource: List<SpinnerItems>) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_spinner_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = dataSource.get(position).name


        val id = context.resources.getIdentifier(
            dataSource.get(position).url,
            "drawable",
            context.packageName
        )
        vh.img.setBackgroundResource(id)

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

  /*  override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        super.getDropDownView(position, convertView, parent)
       convertView?.background = context.resources.getDrawable(R.drawable.spinner_item_state)
        return convertView!!
    }*/

    private class ItemHolder(row: View?) {
        val label: TextView
        val img: ImageView

        init {
            label = row?.findViewById(R.id.text) as TextView
            img = row.findViewById(R.id.img) as ImageView
        }
    }

}