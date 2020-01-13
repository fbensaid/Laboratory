package com.mtp.laboproject.view.ui.fragment

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mtp.laboproject.view.factory.ProfilViewModelFactory
import com.mtp.laboproject.view.ui.activity.AuthentificationActivity
import com.mtp.laboproject.view.viewmodel.ProfilViewModel
import kotlinx.android.synthetic.main.fragment_profil.*
import org.jetbrains.anko.support.v4.intentFor
import com.google.firebase.auth.UserProfileChangeRequest
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.mtp.laboproject.LaboApplication.Companion.auth
import com.mtp.laboproject.R
import com.mtp.laboproject.global.Constants.Variants.RESULT_LOAD_IMG
import com.mtp.laboproject.global.bitmapToByte
import com.mtp.laboproject.global.getRightAngleImage
import permissions.dispatcher.*
import com.mtp.laboproject.global.DebugLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.LinearLayout
import android.widget.EditText
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.support.v4.act

@RuntimePermissions
class ProfilFragment : BaseFragment() , OnMapReadyCallback {


    private lateinit var profilViewModel: ProfilViewModel
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ProfilViewModelFactory()
        profilViewModel = ViewModelProviders.of(this, factory).get(ProfilViewModel::class.java)

        setupUserInfo()
        initMaps()

        switch_finger.setOnCheckedChangeListener { _, b ->
            profilViewModel.getsharedPreference().fingerPrint = b
        }
        switch_notif.setOnCheckedChangeListener { _, b ->
            profilViewModel.getsharedPreference().notification = b
        }
        btn_disconnect.setOnClickListener {
            profilViewModel.getsharedPreference().isStillConnected = false
            startActivity(intentFor<AuthentificationActivity>())
            //this.finish()
        }
        profile_picture.setOnClickListener {
            showImagePickerWithPermissionCheck()
        }
        edit_btn_name.setOnClickListener {
           showDialogueWithEditText()
        }
    }

    private fun initMaps() {
        (this.childFragmentManager.findFragmentById(R.id.map1) as SupportMapFragment?)?.let {
            it.getMapAsync(this)
        }
    }
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        // Add a marker in Sydney and move the camera
        val iff = LatLng(getString(R.string.iff_lat).toDouble(), getString(R.string.iff_lang).toDouble())
        //val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(iff).title("IFF"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(iff))
    }

    private fun showDialogueWithEditText(){
        val input = EditText(context)
        input.hint=auth.currentUser!!.displayName
        input.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        AlertDialog.Builder(activity!!)
            .setCancelable(false)
            .setPositiveButton(R.string.OK) { _, _ ->
                updateFireBaseUser(null,input.text.toString())
                name.text=input.text.toString()
            }
            .setMessage("EditText")
            .setView(input)
            .show()
    }

    private fun setupUserInfo() {
        switch_finger.isChecked = profilViewModel.getsharedPreference().fingerPrint!!
        switch_notif.isChecked = profilViewModel.getsharedPreference().notification!!
        name.text= auth.currentUser!!.displayName
        identifiant_txt.text=auth.currentUser!!.email

        profilViewModel.viewModelScope.launch(Dispatchers.Main.immediate) {
            var bitmap=getRightAngleImage(Uri.parse(profilViewModel.getsharedPreference()!!.userResponse.photoUrl!!))
            if(bitmap !=  null)
            Glide.with(context!!).load(bitmap?.let { bitmapToByte(it) }).into(profile_picture)
        }
    }

    private fun updateFireBaseUser(uri: Uri?, name: String?) {
        var profileUpdates = UserProfileChangeRequest.Builder().apply {
            if(!name.isNullOrEmpty())
                setDisplayName(name)
            if(uri!=null)
                setPhotoUri(uri)
        }.build()
        auth.currentUser!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful)
                DebugLog.d(TAG, "Update User with SUCCESS")
            else
                DebugLog.d(TAG, "Update User with ERROR")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            profilViewModel.viewModelScope.launch {
                Glide.with(context!!).load(
                    getRightAngleImage(data.data!!)
                    ?.let { bitmapToByte(it) }).into(profile_picture)
            }
            updateFireBaseUser(data.data!!, null)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun showRationaleDialog(message: Int, request: PermissionRequest) {
        AlertDialog.Builder(activity!!)
            .setPositiveButton(R.string.OK) { _, _ -> request.proceed() }
            .setNegativeButton(R.string.NO) { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(message)
            .show()
    }

    @NeedsPermission(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun showImagePicker() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        photoPickerIntent.type = "image/*"
        activity!!.startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    @OnShowRationale(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun showRationaleForImagePicker(request: PermissionRequest) {
        showRationaleDialog(R.string.app_name, request)
    }

    @OnPermissionDenied(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onImagePickerDenied() {
        Toast.makeText(activity, R.string.permission_denied, Toast.LENGTH_LONG).show()
    }

    @OnNeverAskAgain(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onImagePickerNeverAskAgain() {
        Toast.makeText(activity, R.string.ask_permission, Toast.LENGTH_LONG).show()
    }

    companion object {

        private val TAG = ProfilFragment::class.toString()
    }


}

