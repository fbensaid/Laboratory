package net.simplifiedcoding.mvvmsampleapp.util

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_auth.*


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
