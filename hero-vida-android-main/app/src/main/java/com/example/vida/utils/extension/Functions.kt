package com.example.vida.utils.extension

import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.vida.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.util.*


object Functions {
    val myCustomDialog: Dialog? = null
    var TAG = "==>"
    private var snackbar: Snackbar? = null


    fun setCustomizeStatusBar(window: Window, statusBarColor: Int, statusTextColor: Boolean) {
        /* statusTextColor is true if only you
        want black color of status bar text*/
        val wic = WindowInsetsControllerCompat(window, window.decorView)
        wic.isAppearanceLightStatusBars = statusTextColor
        window.statusBarColor = statusBarColor
    }

    fun statusColor(context: Activity, statusBarColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = context.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = statusBarColor
        }
    }

    fun makeTransperantStatusBar(activity:Activity,isTransperant: Boolean) {
        if (isTransperant) {
            activity.window
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            activity.window
                .clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    fun colorSpecificText(textToColor: String, fullText: String, targetColor: Int) =
        SpannableStringBuilder(fullText).apply {
            setSpan(
                ForegroundColorSpan(targetColor),
                fullText.indexOf(textToColor),
                (fullText.indexOf(textToColor) + textToColor.length),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

    fun coloredText(
        baseText: String, coloredText: String,
        targetColor: Int
    ): SpannableStringBuilder {
        val transformText = "$baseText $coloredText"
        return SpannableStringBuilder(transformText).apply {
            setSpan(
                ForegroundColorSpan(targetColor),
                transformText.indexOf(coloredText),
                (transformText.indexOf(coloredText) + coloredText.length),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }


    fun EditText.afterTextChanged(
        afterTextChanged: (String) -> Unit,
        onTextChange: (String) -> Unit
    ) {
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


    fun shareIntent(context: Context) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://vida--stage.herokuapp.com/vida/location?secret=eyJsYXQiOiIyOC42NDQ4MDAiLCJsb25nIjoiNzcuMjE2NzIxIn0=")
            type = "text/plain"
        }
        context.startActivity(sendIntent)
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "IMG_" + System.currentTimeMillis(),
            null
        )
        return Uri.parse(path)
    }

    fun textStyle(bold: String, space: String?, nText: String?): Spanned {
        val sourceString: String = if (space.equals("")) {
            "<b>$bold</b> $nText"
        } else {
            "<b>$bold</b> $space $nText"
        }
        return HtmlCompat.fromHtml(sourceString, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun snackBarMessage(context: Context, view: View?) {
        snackbar = Snackbar.make(
            view!!, context.resources.getString(R.string.no_internet_found),
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar!!.view.setBackgroundColor(Color.RED)
        snackbar!!.show()
    }

    fun hideSnackBar() {
        if (snackbar != null) snackbar!!.dismiss()
    }

    val isSnackBarShowing: Boolean
        get() = snackbar != null && snackbar!!.isShown

    fun getAddressFromLatLng(context: Context, LATITUDE: Double, LONGITUDE: Double): String {
        var strAdd = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.d(">==", "My Current location address $strReturnedAddress")
            } else {
                Log.w(">==", "My Current location address" + "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(">==", "My Current location address" + "Cannot get Address!")
        }
        return strAdd
    }

    fun getFilePath(context: Context, uri: Uri): String? {
        var uri = uri
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(
                context.getApplicationContext(),
                uri
            )
        ) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("image" == type) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                selection = "_id=?"
                selectionArgs = arrayOf(
                    split[1]
                )
            }
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            if (isGooglePhotosUri(uri)) {
                return uri.lastPathSegment
            }
            val projection = arrayOf(
                MediaStore.Images.Media.DATA
            )
            var cursor: Cursor? = null
            try {
                cursor = context.getContentResolver()
                    .query(uri, projection, selection, selectionArgs, null)
                val column_index: Int =
                    cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)!!
                if (cursor?.moveToFirst() == true) {
                    return cursor.getString(column_index)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)
        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)
        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}