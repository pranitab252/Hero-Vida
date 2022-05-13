package com.example.vida.utils.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.opengl.Visibility
import android.location.Address
import android.location.Geocoder
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.vida.R
import com.google.android.gms.maps.model.LatLng
import com.otc.alice.api.model.ProfileAndSettings
import java.text.SimpleDateFormat
import java.util.*

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit, onTextChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChange.invoke(s.toString())
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun View.invisible(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

inline fun <reified T : Activity> Activity.navigate() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

fun Context.showToast(
    textStr: String,
    textSubStr: String,
    textTrackStr: String,
    isSuccess: Boolean
) {
    val inflater = LayoutInflater.from(this)
    val layout: View = inflater.inflate(R.layout.toast_error, null)
    val text: TextView = layout.findViewById(R.id.toast_text)
    val textSub: TextView = layout.findViewById(R.id.toast_sub_text)
    val textTrack: TextView = layout.findViewById(R.id.toast_track_text)
    if (isSuccess) {
        layout.findViewById<ImageView>(R.id.t_icon)
            .setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_alerts_success))
//        layout.findViewById<RelativeLayout>(R.id.t_background).backgroundTintList =
//            ContextCompat.getColorStateList(this, R.color.colorWhite)
    } else {
        layout.findViewById<ImageView>(R.id.t_icon)
            .setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_error_toast))
        layout.findViewById<RelativeLayout>(R.id.t_background).background =
            resources.getDrawable(R.drawable.toast_white_rounded)
        textSub.visibility = View.VISIBLE
//        textTrack.visibility = View.VISIBLE
        textTrack.isVisible = textTrackStr.isNotEmpty()
        textSub.text = textSubStr
        textTrack.text = textTrackStr
    }
    text.text = textStr
    with(Toast(this)) {
        setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}

fun Context.showToastSuccess(mTitle: String, mDesc: String, isSuccess: Boolean) {
    val inflater = LayoutInflater.from(this)
    val layout: View = inflater.inflate(R.layout.toast, null)
    val text: TextView = layout.findViewById(R.id.toast_text)
    val textDesc: TextView = layout.findViewById(R.id.toast_desc)
    if (isSuccess) {
        layout.findViewById<ImageView>(R.id.t_icon)
            .setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_alerts_success))
//        layout.findViewById<LinearLayout>(R.id.t_background).backgroundTintList =
//            ContextCompat.getColorStateList(this, R.color.colorWhite)
        textDesc.hide()
    } else {
        layout.findViewById<ImageView>(R.id.t_icon)
            .setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_alerts_success))
        layout.findViewById<LinearLayout>(R.id.t_background).background =
            resources.getDrawable(R.drawable.toast_white_rounded, null)
        textDesc.show()
    }
    text.text = mTitle
    textDesc.text = mDesc
    with(Toast(this)) {
        setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}

fun Canvas.drawLeftRoundRect(rect: RectF, paint: Paint, radius: Float) {
    // Step 1. Draw rect with rounded corners.
    drawRoundRect(rect, radius, radius, paint)

    // Step 2. Draw simple rect with reduced height,
    // so it wont cover top rounded corners.
    drawRect(
        rect.left + radius,
        rect.top,
        rect.right,
        rect.bottom,
        paint
    )
}

fun Canvas.drawRightRoundRect(rect: RectF, paint: Paint, radius: Float) {
    // Step 1. Draw rect with rounded corners.
    drawRoundRect(rect, radius, radius, paint)

    // Step 2. Draw simple rect with reduced height,
    // so it wont cover top rounded corners.
    drawRect(
        rect.left,
        rect.top,
        rect.right - radius,
        rect.bottom,
        paint
    )
}


fun Fragment.getSafeContextOrNull(): Context? {
    this.context?.let {
        return it
    }
    this.activity?.let {
        return it
    }
    return null
}


fun ProfileAndSettings.UserNotification.isWithinThreshold(): Boolean {
    val calendar = Calendar.getInstance()
    val dateFormat = Constants.DATE_TIME_FORMAT_DB_WITH_MILLIS
    val now = SimpleDateFormat(dateFormat, Locale.getDefault()).parse(
        SimpleDateFormat(
            dateFormat,
            Locale.getDefault()
        ).format(calendar.time)
    )

    val dateTime = SimpleDateFormat(dateFormat, Locale.getDefault()).parse(this.timestamp)

    calendar.timeInMillis = dateTime.time


    val alertTime = SimpleDateFormat(dateFormat, Locale.getDefault()).parse(
        SimpleDateFormat(
            dateFormat,
            Locale.getDefault()
        ).format(calendar.time)
    )

    calendar.timeInMillis = dateTime.time
    calendar.add(Calendar.HOUR, -1)


    val isThresholdMet = !now.before(alertTime)
    Utils.printDevLog(
        mssg = "UserNotification.isWithinThreshold: $isThresholdMet"
    )

    return isThresholdMet
}

fun LatLng.getLocationTextFromLatLng(ctx: Context, showErrorToastIfAny: Boolean = false): String? {
    Utils.printDevLog(mssg = "\ngetLocationTextFromLatLng: lat $latitude")
    Utils.printDevLog(mssg = "\ngetLocationTextFromLatLng: lng $longitude")
    val geocoder = Geocoder(ctx, Locale.ENGLISH)
    var locText: String? = null

    val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
    if (addresses.isEmpty()) {
        if(showErrorToastIfAny) ctx.showToast("Location could not be detected!", "", "", false)
        Utils.printDevLog(mssg = "addresses.isEmpty()")
        Utils.printDevLog(mssg = "further part: skipping")
    } else {
        Utils.printDevLog(mssg = "\naddresses: $addresses")
        locText = addresses[0].subAdminArea
        locText += ", ${addresses[0].adminArea}"
        Utils.printDevLog(mssg = "locText: $locText")
    }

    return locText
}

fun LatLng.getLocationTextFromLatLngForTrips(ctx: Context, showErrorToastIfAny: Boolean = false): String? {
    Utils.printDevLog(mssg = "\ngetLocationTextFromLatLng: lat $latitude")
    Utils.printDevLog(mssg = "\ngetLocationTextFromLatLng: lng $longitude")
    val geocoder = Geocoder(ctx, Locale.ENGLISH)
    var locText: String? = null

    val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
    if (addresses.isEmpty()) {
        if(showErrorToastIfAny) ctx.showToast("Location could not be detected!", "", "", false)
        Utils.printDevLog(mssg = "addresses.isEmpty()")
        Utils.printDevLog(mssg = "further part: skipping")
    } else {
        Utils.printDevLog(mssg = "\naddresses: $addresses")
        locText = addresses[0].subAdminArea
        locText += ", ${addresses[0].adminArea}"
        Utils.printDevLog(mssg = "locText: $locText")
    }

    return locText
}