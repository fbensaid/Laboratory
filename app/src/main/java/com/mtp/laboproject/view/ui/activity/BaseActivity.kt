package com.mtp.laboproject.view.ui.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Camera
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.R
import com.mtp.laboproject.global.AppUtils
import com.mtp.laboproject.global.Constants
import com.mtp.laboproject.global.Constants.Requests.REQUEST_CALL_SETTINGS
import com.mtp.laboproject.global.Constants.Requests.REQUEST_CAMERA_SETTINGS
import com.mtp.laboproject.global.SharedPreferences
import com.mtp.laboproject.listener.RepositoryListener
import com.mtp.laboproject.view.ui.view.CustomProgressDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_layout.*
import org.jetbrains.anko.find
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    private lateinit var  mProgressDialog: CustomProgressDialog
    @Inject
    lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        LaboApplication.appComponent.inject(this)
    }

    protected fun getPreferences(): SharedPreferences {
        return sharedPreferences
    }


    private fun handleNotification(intent: Intent?) {
        if (intent != null && intent.extras != null && intent.extras!!.containsKey(Constants.Parameters.PUSH_DATA_type) && intent.extras!!.containsKey(
                Constants.Parameters.PUSH_DATA_MESSAGE
            )
        ) {
            val type = intent.extras!!.getString(Constants.Parameters.PUSH_DATA_type, "")
            val message = intent.extras!!.getString(Constants.Parameters.PUSH_DATA_MESSAGE, "")

            if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(message)) {
                when (type) {


                    else -> showSimpleOkDialog(message)
                }
            }
        }
    }



    fun quitApp() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
        System.exit(0)
    }

    /**
     * show subscribe ok dialog
     */

    fun showSimpleOkDialog(message: String, okAction: Runnable) {
        showSimpleOkDialog(message, 0, 0, R.string.ok, okAction)
        // new CustomPickUPDialog(this, CustomDialogType.giftWeek, null,null,null) ;
    }

    fun showSimpleOkDialog(message: String) {
        showSimpleOkDialog(message, 0, 0, R.string.ok, null)
    }

    fun showSimpleOkDialog(@StringRes msgId: Int) {
        showSimpleOkDialog(null, 0, msgId, R.string.ok, null)
    }

    fun showSimpleOkDialog(@StringRes msgId: Int, okAction: Runnable) {
        showSimpleOkDialog(null, 0, msgId, R.string.ok, okAction)
    }

    fun showSimpleOkDialog(@StringRes titleId: Int, @StringRes btnText: Int, @StringRes msgId: Int) {
        showSimpleOkDialog(null, titleId, msgId, btnText, null)
    }

    fun showSimpleOkDialog(
        @StringRes titleId: Int, @StringRes msgId: Int, @StringRes btnText: Int, okAction: Runnable
    ) {
        showSimpleOkDialog(null, titleId, msgId, btnText, null)
    }


    fun showSimpleOkDialog(@StringRes msgId: Int, @StringRes btnText: Int) {
        showSimpleOkDialog(null, 0, msgId, btnText, null)
    }

    /**
     * show subscribe ok dialog
     *
     * @param msgId    message resources id
     * @param okAction action to do on click
     */
    fun showSimpleOkDialog(
        message: String?, @StringRes titleId: Int, @StringRes msgId: Int, @StringRes btnText: Int,
        okAction: Runnable?
    ) {
        if (!isFinishing) {

            val dialogBuilder =
                AlertDialog.Builder(ContextThemeWrapper(this, R.style.custom_error_dialog))
            val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.setCancelable(if (okAction == null) true else false)
            alertDialog.setCanceledOnTouchOutside(false)

            val titleTextView = tv_first_end_action
            if (titleId != 0) {
                titleTextView.setVisibility(View.VISIBLE)
                titleTextView.setText(getString(titleId))
            } else {
                titleTextView.setVisibility(View.GONE)
            }


            val contentTextView = findViewById<TextView>(R.id.tv_content)
            if (message != null && !TextUtils.isEmpty(message)) {
                contentTextView.setText(message)
            } else {
                contentTextView.setText(getString(msgId))
            }

            val okBtn = btn_ok
            okBtn.setText(btnText)
            okBtn.setOnClickListener(View.OnClickListener {
                alertDialog.dismiss()
                okAction?.run()
            })

            val cancelBtn = btn_cancel
            cancelBtn.setVisibility(View.GONE)

            alertDialog.show()
        }
    }


    fun showSimpleOkDialog(
        @StringRes titleId: Int, @StringRes msgId: Int, @StringRes btnText: Int, textData: String,
        okAction: Runnable?
    ) {
        if (!isFinishing) {


            val dialogBuilder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.setCancelable(if (okAction == null) true else false)

            val titleTextView = tv_first_end_action
            if (titleId != 0) {
                titleTextView.setVisibility(View.VISIBLE)
                titleTextView.setText(getString(titleId))
            } else {
                titleTextView.setVisibility(View.GONE)
            }


            val contentTextView = tv_content

            contentTextView.setText(getString(msgId, textData))

            val okBtn = btn_ok
            okBtn.setText(btnText)
            okBtn.setOnClickListener(View.OnClickListener {
                alertDialog.dismiss()
                okAction?.run()
            })

            val cancelBtn = btn_cancel
            cancelBtn.setVisibility(View.GONE)

            alertDialog.show()
        }
    }

    /**
     * show subscribe ok dialog
     *
     * @param msgId    message resources id
     * @param okId     ok button resources id
     * @param cancelId cancel button resources id
     * @param okAction action to do on click ok
     */
    fun showChoseDialog(
        @StringRes msgId: Int, @StringRes okId: Int, @StringRes cancelId: Int, okAction: Runnable
    ) {
        showChoseDialog(msgId, okId, cancelId, okAction, null)
    }

    fun showChoseDialog(
        @StringRes titleId: Int, @StringRes msgId: Int, @StringRes okId: Int, @StringRes cancelId: Int,
        okAction: Runnable
    ) {
        showChoseDialog(titleId, msgId, okId, cancelId, okAction, null)
    }


    /**
     * show subscribe ok dialog
     *
     * @param msgId        message resources id
     * @param okId         ok button resources id
     * @param cancelId     cancel button resources id
     * @param okAction     action to do on click ok
     * @param cancelAction action to do on click cancel
     */
    fun showChoseDialog(
        @StringRes msgId: Int, @StringRes okId: Int, @StringRes cancelId: Int, okAction: Runnable?,
        cancelAction: Runnable?
    ) {
        if (!isFinishing) {

            val dialogBuilder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.setCancelable(if (okAction == null) true else false)


            val titleTextView = tv_first_end_action
            titleTextView.setVisibility(View.GONE)

            val contentTextView = tv_content
            contentTextView.setText(getString(msgId))

            val okBtn = btn_ok
            okBtn.setVisibility(View.VISIBLE)
            okBtn.setText(okId)
            okBtn.setOnClickListener(View.OnClickListener {
                alertDialog.dismiss()
                okAction?.run()
            })

            val cancelBtn =btn_cancel
            cancelBtn.setVisibility(View.VISIBLE)
            cancelBtn.setText(cancelId)
            cancelBtn.setOnClickListener(View.OnClickListener {
                alertDialog.dismiss()
                cancelAction?.run()
            })

            alertDialog.show()
        }
    }

    fun showChoseDialog(
        @StringRes titleId: Int, @StringRes msgId: Int, @StringRes okId: Int, @StringRes cancelId: Int,
        okAction: Runnable?,
        cancelAction: Runnable?
    ) {
        if (!isFinishing) {

            val dialogBuilder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.setCancelable(if (okAction == null) true else false)


            val titleTextView = tv_first_end_action
            titleTextView.setVisibility(View.VISIBLE)
            titleTextView.setText(titleId)

            val contentTextView = tv_content
            contentTextView.setText(getString(msgId))

            val okBtn = btn_ok
            okBtn.setVisibility(View.VISIBLE)
            okBtn.setText(okId)
            okBtn.setOnClickListener(View.OnClickListener {
                alertDialog.dismiss()
                okAction?.run()
            })

            val cancelBtn = btn_cancel
            cancelBtn.setVisibility(View.VISIBLE)
            cancelBtn.setText(cancelId)
            cancelBtn.setOnClickListener(View.OnClickListener {
                alertDialog.dismiss()
                cancelAction?.run()
            })

            alertDialog.show()
        }
    }

    /**
     * show unavailable Network Error
     */
    fun showErrorNetworkDialog() {

        showSimpleOkDialog(R.string.error_network, R.string.ok)
    }

    /**
     * show server Error
     */
    fun showErrorServerDialog() {

        showSimpleOkDialog(R.string.error_server, R.string.ok)
    }

    /**
     * show default ws dialog fail handler
     */
    fun showDefaultFailDialog(failType: RepositoryListener.FailType) {
        when (failType) {

            RepositoryListener.FailType.NETWORK -> showErrorNetworkDialog()
            RepositoryListener.FailType.UNAUTHORIZED -> showSimpleOkDialog(R.string.expired_session, getUnauthorizedAction())

            else -> showSimpleOkDialog(R.string.error_server, R.string.ok)
        }
    }





    /**
     * show blocking progressBar on the root of the activity
     */
    fun showProgressBar() {

        if (!isFinishing) {

            if (mProgressDialog == null) {
                mProgressDialog = CustomProgressDialog(this, R.style.ProgressDialogStyle)
                (mProgressDialog as CustomProgressDialog).setCancelable(false)

            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show()
            }

        }
    }

    /**
     * hide blocking progressBar
     */
    fun hideProgressBar() {

        if (!isFinishing) {

            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss()
            }
        }
    }

    @NonNull
    fun getUnauthorizedAction(): Runnable {
        return Runnable { navigateToSignIn() }
    }

    private fun navigateToSignIn() {


        val intent = Intent(this@BaseActivity, AuthentificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        //   overridePendingTransition(R.anim.slide_in_up, R.anim.non);

    }


    /**
     * Start new activity when the app on foreground
     */
    fun startActivityWhenForeground(intent: Intent) {

        startActivityWhenForeground(intent, false)
    }


    /**
     * Start new activity when the app on foreground
     */
    fun startActivityWhenForeground(intent: Intent, shouldFinish: Boolean) {


    }


    /**
     * Start new activity when the app on foreground
     */
    fun startActivityWhenForeground(activity: Class<*>) {

        startActivityWhenForeground(activity, false)

    }

    /**
     * Start new activity when the app on foreground
     *
     * @param shouldFinish current activity
     */

    fun startActivityWhenForeground(activity: Class<*>, shouldFinish: Boolean) {

        val intent = Intent(this@BaseActivity, activity)
        startActivityWhenForeground(intent, shouldFinish)

    }


    @NonNull
    private fun buildStartRunnable(intent: Intent, shouldFinish: Boolean): Runnable {
        return Runnable {

            startActivity(intent)

            if (shouldFinish) finish()

        }
    }


    /**
     * Start new activity for result when the app on foreground
     */
    fun startActivityForResultWhenForeground(intent: Intent, requestCode: Int) {

        startActivityForResultWhenForeground(intent, false, requestCode)
    }


    /**
     * Start new activity for result when the app on foreground
     */
    fun startActivityForResultWhenForeground(
        intent: Intent,
        shouldFinish: Boolean,
        requestCode: Int
    ) {


    }


    /**
     * Start new activity for result when the app on foreground
     */
    fun startActivityForResultWhenForeground(activity: Class<*>, requestCode: Int) {

        startActivityForResultWhenForeground(activity, false, requestCode)

    }

    /**
     * Start new activity for result when the app on foreground
     *
     * @param shouldFinish current activity
     */

    fun startActivityForResultWhenForeground(
        activity: Class<*>,
        shouldFinish: Boolean,
        requestCode: Int
    ) {

        val intent = Intent(this@BaseActivity, activity)
        startActivityForResultWhenForeground(intent, shouldFinish, requestCode)

    }


    @NonNull
    private fun buildStartForResultRunnable(
        intent: Intent,
        shouldFinish: Boolean,
        requestCode: Int
    ): Runnable {
        return Runnable {


            startActivityForResult(intent, requestCode)

            if (shouldFinish) finish()

        }
    }

    /**
     * show error network
     */
    fun isNetworkAvailable(): Boolean {
        return AppUtils.isNetworkAvailable(this)
    }

    /**
     * try to hide Keyboard from the focused screen
     */
    fun hideKeyboard() {
        try {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.getWindowToken(), 0)
        } catch (e: Exception) {
        }

    }


    private fun cancelProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss()
        }
    }

    private fun pendingTransition() {

        //overridePendingTransition(R.anim.nothing, R.anim.bottom_down)

    }

    fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_SETTINGS
        )
    }

    fun requestCallPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CALL_PHONE),
            REQUEST_CALL_SETTINGS
        )
    }


    fun checkAndroidVersion(@NonNull permissions: Array<String>) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val showRationale = shouldShowRequestPermissionRationale(permissions[0])
            //getPreferences().setNeverLocation(!showRationale);
        }

        //   finish();
    }


}