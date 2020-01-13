package com.mtp.laboproject.global

import android.Manifest
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.mtp.laboproject.LaboApplication
import permissions.dispatcher.NeedsPermission
import android.provider.MediaStore
import java.io.ByteArrayOutputStream


fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
}

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.GONE
}

fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

 fun EditText.validateForm(): Boolean {
    var valid = true

    if (TextUtils.isEmpty(this.text)) {
        this.error = "Required."
        valid = false
    } else {
        this.error = null
    }
    return valid
}

@NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
 suspend fun getRightAngleImage(path:Uri): Bitmap? {
    val bitmap : Bitmap? = null
    try {
        val bitmap = MediaStore.Images.Media.getBitmap(LaboApplication.instance.contentResolver, path)
        var file = LaboApplication.instance.contentResolver.openInputStream(path)
        val ei = ExifInterface(file!!)
        val orientation =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        var degree = 0F

        degree = when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> 0F
            ExifInterface.ORIENTATION_ROTATE_90 -> 90F
            ExifInterface.ORIENTATION_ROTATE_180 -> 180F
            ExifInterface.ORIENTATION_ROTATE_270 -> 270F
            ExifInterface.ORIENTATION_UNDEFINED -> 0F
            else -> 90F
        }
        return bitmap.rotate(degree)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return bitmap
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

 fun bitmapToByte(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}



