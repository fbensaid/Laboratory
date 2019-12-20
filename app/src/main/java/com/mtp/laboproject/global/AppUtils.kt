package com.mtp.laboproject.global



import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.annotation.Nullable
import androidx.core.content.FileProvider


import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.mtp.laboproject.BuildConfig
import com.mtp.laboproject.R

import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created on 2/2/18.
 */

object AppUtils {

    private val TAG = AppUtils::class.java.simpleName





    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, @Nullable context: Context?): Float {
        if (context == null)
            return 0f
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi / 160f)
    }

    fun checkPlayServices(context: Activity): Boolean {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(context)
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(
                    context, result,
                    Constants.Requests.PLAY_SERVICES_RESOLUTION_REQUEST
                ).show()
            }

            return false
        }

        return true
    }


    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into
     * pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, @Nullable context: Context?): Float {
        if (context == null)
            return 0f
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }

    /**
     * ic_status_validated if user is connected to the Internet
     *
     * @return A boolean value
     */
    fun isNetworkAvailable(@Nullable context: Context?): Boolean {
        if (context != null) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected

        }

        return false
    }

    fun getFormattedDate(oldDate: String): String? {
        try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH+mm:ss", Locale.US).parse(oldDate)
            val dt1 = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            return dt1.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }


    fun share(giftName: String, context: Context) {

        val imagePath = File(context.cacheDir, "images")
        val newFile = File(imagePath, "image.png")
        val contentUri =
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", newFile)

        if (contentUri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.setDataAndType(contentUri, context.contentResolver.getType(contentUri!!))
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                String.format(
                    context.resources.getString(R.string.share_text),
                    Constants.Urls.APP_URL,
                    giftName
                )
            )
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)

            shareIntent.type = "image/*"
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.getString(R.string.share_subject)
                )
            )
        }

    }

    fun parseSimpleDateToddMMyyyy(time: String): String? {
        val inputPattern = "dd/MM/yyyy"
        val outputPattern = "dd MMMM yyyy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }

    fun parseDateAndTimeToddMMyyyy(time: String): String? {
        val inputPattern = "dd-MM-yyyy HH:mm:ss"
        val outputPattern = "dd-MMM-yyyy h:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }

    fun now(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(Constants.Parameters.DATE_FORMAT_NOW)
        return sdf.format(cal.time)
    }


    fun picassoImageTarget(context: Context): Target {
        val cw = ContextWrapper(context)
        val cachePath = File(cw.cacheDir, "images")
        cachePath.mkdirs()// path to /data/data/yourapp/app_imageDir
        return object : Target {
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                Thread(Runnable {
                    // save bitmap to cache directory
                    try {

                        val cachePath = File(context.cacheDir, "images")
                        cachePath.mkdirs() // don't forget to make the directory
                        val stream =
                            FileOutputStream("$cachePath/image.png") // overwrites this image every time
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        stream.close()

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }).start()
            }

            fun onBitmapFailed(errorDrawable: Drawable) {}

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                if (placeHolderDrawable != null) {
                }
            }
        }


    }

    fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.FRENCH)
        cal.timeInMillis = time * 1000
        return DateFormat.format("dd/MM/yyyy", cal).toString()
    }


    fun md5(message: String): String {
        try {

            val digest = java.security.MessageDigest
                .getInstance("MD5")
            digest.update(message.toByteArray())
            val messageDigest = digest.digest()

            val hexString = StringBuffer()
            for (i in messageDigest.indices) {
                var h = Integer.toHexString(0xFF and messageDigest[i].toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun dpToPx(dp: Float, context: Context): Int {
        return dpToPx(dp, context.resources)
    }

    fun dpToPx(dp: Float, resources: Resources): Int {
        val px =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        return px.toInt()
    }

    /**
     * Make the first letter an upper case
     */
    fun capitalizeFirstLetter(name: String): String? {
        return if (!TextUtils.isEmpty(name)) {
            name.substring(0, 1).toUpperCase() + name.substring(1)
        } else name
    }


    fun rotate(spiral: View) {
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = Constants.Durations.DURATION_ANIMATION * 30
        rotate.repeatMode = Animation.RESTART
        rotate.repeatCount = Animation.INFINITE
        rotate.fillAfter = true
        rotate.interpolator = LinearInterpolator()
        spiral.startAnimation(rotate)
    }


}
