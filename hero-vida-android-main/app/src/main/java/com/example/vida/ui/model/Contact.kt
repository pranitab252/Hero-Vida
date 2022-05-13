package com.example.vida.ui.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BaseObservable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.Serializable


@SuppressLint("ParcelCreator")
class Contact() : Parcelable {
    var name: String? = null
    var phoneNumber: String? = null
    var photoUri: String? = null
    var isChecked: Boolean = false

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        phoneNumber = parcel.readString()
        photoUri = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phoneNumber)
        parcel.writeString(photoUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view)
        }
    }
}
