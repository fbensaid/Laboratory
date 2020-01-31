package com.mtp.laboproject.view.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.mtp.laboproject.R
import com.mtp.laboproject.global.Constants
import com.mtp.laboproject.global.Constants.Requests.MY_PERMISSIONS_REQUEST_LOCATION
import com.mtp.laboproject.global.Constants.Variants.RESULT_LOAD_IMG
import com.mtp.laboproject.global.bitmapToByte
import com.mtp.laboproject.global.getRightAngleImage
import com.mtp.laboproject.view.factory.ProfilViewModelFactory
import com.mtp.laboproject.view.ui.activity.AuthentificationActivity
import com.mtp.laboproject.view.viewmodel.ProfilViewModel
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.intentFor
import permissions.dispatcher.*

@RuntimePermissions
class ProfilFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnInfoWindowClickListener, DialogInterface.OnKeyListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var profilViewModel: ProfilViewModel
    private lateinit var mMap: GoogleMap
    private var mUserMarker: Marker? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

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
        fusedLocationProviderClient = FusedLocationProviderClient(activity!!)

        setupUserInfo()
        initMaps()

        // Here, thisActivity is the current activity
        askForLocationPermissionWithPermissionCheck()

        switch_finger.setOnCheckedChangeListener { _, b ->
            profilViewModel.getsharedPreference().fingerPrint = b
        }
        switch_notif.setOnCheckedChangeListener { _, b ->
            profilViewModel.getsharedPreference().notification = b
        }
        btn_disconnect.setOnClickListener {
            profilViewModel.getsharedPreference().isStillConnected = false
            startActivity(intentFor<AuthentificationActivity>())
        }
        profile_picture.setOnClickListener {
            showImagePickerWithPermissionCheck()
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
     fun askForLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
        }

    fun getLastLocation() { // Get last known recent location using new Google Play Services SDK (v11+)
        val locationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
        )
            return

        locationClient.lastLocation
            .addOnSuccessListener { location ->
                // GPS location can be null if GPS is switched off
                if (location != null) {
                    setUserMarker(location, R.drawable.marker_ic)
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun initMaps() {
        (this.childFragmentManager.findFragmentById(R.id.map1) as SupportMapFragment?)?.let {
            it.getMapAsync(this)
        }
        getLastLocation()
    }

    fun setUserMarker(location: Location?, drawable: Int) {
        if (mUserMarker != null && location != null) {
            mUserMarker!!.remove()
        }
        if (location != null) {
            val markerOptions = MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_ic))
                .position(LatLng(location.latitude, location.longitude))
            if (mMap != null) {
                mUserMarker = mMap.addMarker(markerOptions)
                val cameraPosition = CameraPosition.Builder()
                    .target(LatLng(location.latitude, location.longitude))
                    .zoom(Constants.CURRENT_POSITION_ZOOM.toFloat())
                    .build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }
    }


    private fun setupUserInfo() {
        switch_finger.isChecked = profilViewModel.getsharedPreference().fingerPrint!!
        switch_notif.isChecked = profilViewModel.getsharedPreference().notification!!
        name.text = profilViewModel.getsharedPreference().userResponse.data.USERNAME
        identifiant_txt.text = profilViewModel.getsharedPreference().userResponse.data.USR_MAIL

        profilViewModel.viewModelScope.launch(Dispatchers.Main.immediate) {
            if( profilViewModel.getsharedPreference()!!.userResponse.data.photoUrl!=null){
            var bitmap = getRightAngleImage(Uri.parse(profilViewModel.getsharedPreference()!!.userResponse.data.photoUrl!!))
            if (bitmap != null)
                    Glide.with(context!!).load(bitmap?.let { bitmapToByte(it) }).into(profile_picture)
            }else
                Glide.with(context!!).load(R.drawable.user).into(profile_picture)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map ?: return
        if (isPermissionGiven()) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = (10 * 1000).toLong()
        locationRequest.fastestInterval = 2000

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val locationSettingsRequest = builder.build()

        val result = LocationServices.getSettingsClient(activity!!)
            .checkLocationSettings(locationSettingsRequest)
        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                if (response!!.locationSettingsStates.isLocationPresent) {
                    getLastLocation()
                }
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvable = exception as ResolvableApiException
                        resolvable.startResolutionForResult(
                            activity!!,
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    } catch (e: IntentSender.SendIntentException) {
                    } catch (e: ClassCastException) {
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        }
    }

    private fun isPermissionGiven(): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun updatePhotoUserUri(uri: Uri?) {
        profilViewModel.getsharedPreference().userResponse.data.photoUrl= uri.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            profilViewModel.viewModelScope.launch {
                Glide.with(context!!).load(
                    getRightAngleImage(data.data!!)
                        ?.let { bitmapToByte(it) }).into(profile_picture)
            }
            if (data.data != null) {
                updatePhotoUserUri(data.data!!)
            }
        }
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (resultCode == Activity.RESULT_OK) {
                    getCurrentLocation()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getCurrentLocation()
                } else {
                    Toast.makeText(activity, R.string.permission_denied, Toast.LENGTH_LONG).show()
                }
                return
            }
        }

    }

    private fun showRationaleDialog(message: Int, request: PermissionRequest) {
        AlertDialog.Builder(activity!!)
            .setPositiveButton(R.string.OK) { _, _ -> request.proceed() }
            .setNegativeButton(R.string.NO) { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(message)
            .setOnKeyListener(this)
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
    @NeedsPermission(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    fun showLocationDialog() {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
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
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION
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

    override fun onMapClick(p0: LatLng?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInfoWindowClick(p0: Marker?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        getCurrentLocation()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

