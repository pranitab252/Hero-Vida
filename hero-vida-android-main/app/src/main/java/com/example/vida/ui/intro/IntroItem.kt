package com.example.vida.ui.intro

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IntroItem(val imgResId: Int, val title: String, val description: String) :
        Parcelable