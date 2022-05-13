package com.example.vida.ui.dashboard.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vida.databinding.PanicAlertRecyclerItemBinding
import com.example.vida.ui.dashboard.adapter.PanicContactRecyclerAdapter.*
import com.example.vida.ui.model.Contact
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import java.util.*

class PanicContactRecyclerAdapter(
    private val context: Context,
    private val contactList: List<Contact>
) : RecyclerView.Adapter<PanicContactRecyclerViewHolder>() {
    private var mOnItemClickListener: OnItemClickListener? = null
    inner class PanicContactRecyclerViewHolder(panicAlertRecyclerItemBinding: PanicAlertRecyclerItemBinding) :
        RecyclerView.ViewHolder(panicAlertRecyclerItemBinding.root) {
        var contentPanicBinding: PanicAlertRecyclerItemBinding = panicAlertRecyclerItemBinding

    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PanicContactRecyclerViewHolder {
        return PanicContactRecyclerViewHolder(
            PanicAlertRecyclerItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PanicContactRecyclerViewHolder, position: Int) {
        val contacts = contactList[position]
        holder.contentPanicBinding.contactName.text = contacts.name
        if (contacts.photoUri != null) {
            holder.contentPanicBinding.drawableTextView.hide()
            holder.contentPanicBinding.contactPhoto.show()
            Contact.loadImage(holder.contentPanicBinding.contactPhoto, contacts.photoUri)
        } else {
            holder.contentPanicBinding.contactPhoto.hide()
            holder.contentPanicBinding.drawableTextView.show()
            val gradientDrawable =
                holder.contentPanicBinding.drawableTextView.background as GradientDrawable
            gradientDrawable.setColor(randomColor)
            val serviceSubString = contacts.name!!.substring(0, 2)
            holder.contentPanicBinding.drawableTextView.text = serviceSubString.toUpperCase()
        }
        holder.contentPanicBinding.ivTrashPanic.setOnClickListener {
            mOnItemClickListener!!.onItemClick(contacts)
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    private val randomColor: Int
        private get() {
            val rnd = Random()
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

    interface OnItemClickListener {
        fun onItemClick(contacts: Contact)
    }
}