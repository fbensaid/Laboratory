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
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
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
import com.google.firebase.auth.UserProfileChangeRequest
import com.mtp.laboproject.LaboApplication.Companion.auth
import com.mtp.laboproject.R
import com.mtp.laboproject.global.Constants
import com.mtp.laboproject.global.Constants.Requests.MY_PERMISSIONS_REQUEST_LOCATION
import com.mtp.laboproject.global.Constants.Variants.RESULT_LOAD_IMG
import com.mtp.laboproject.global.DebugLog
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
    private val REQUEST_CHECK_SETTINGS = 43

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
        edit_btn_name.setOnClickListener {
            showDialogueWithEditText()
        }

    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
     fun askForLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    fun getLastLocation() { // Get last known recent location using new Google Play Services SDK (v11+)
        val locationClient =
            LocationServices.getFusedLocationProviderClient(activity!!)
        if (ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) { // TODO: Consider calling
//    ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                          int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
            return
        }
        locationClient.lastLocation
            .addOnSuccessListener { location ->
                // GPS location can be null if GPS is switched off
                if (location != null) {
                    //onLocationChanged(location)
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
    /*override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        // Add a marker in Sydney and move the camera
        val iff = LatLng(getString(R.string.iff_lat).toDouble(), getString(R.string.iff_lang).toDouble())
        //val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(iff).title("IFF"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(iff))
    }*/

    private fun showDialogueWithEditText() {
        val input = EditText(context)
        input.hint = auth.currentUser!!.displayName
        input.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        AlertDialog.Builder(activity!!)
            .setCancelable(false)
            .setPositiveButton(R.string.OK) { _, _ ->
                updateFireBaseUser(null, input.text.toString())
                name.text = input.text.toString()
            }
            .setMessage("EditText")
            .setView(input)
            .show()
    }

    private fun setupUserInfo() {
        switch_finger.isChecked = profilViewModel.getsharedPreference().fingerPrint!!
        switch_notif.isChecked = profilViewModel.getsharedPreference().notification!!
        name.text = auth.currentUser!!.displayName
        identifiant_txt.text = auth.currentUser!!.email

        profilViewModel.viewModelScope.launch(Dispatchers.Main.immediate) {
            var bitmap =
                getRightAngleImage(Uri.parse(profilViewModel.getsharedPreference()!!.userResponse.photoUrl!!))
            if (bitmap != null)
                Glide.with(context!!).load(bitmap?.let { bitmapToByte(it) }).into(profile_picture)
        }
    }


    override fun onMapReady(map: GoogleMap?) {
        mMap = map ?: return
        if (isPermissionGiven()) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
            getCurrentLocation()
        } else {


        }
    }


    private fun givePermission() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            !== PackageManager.PERMISSION_GRANTED
        ) { // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                android.app.AlertDialog.Builder(activity)
                    .setTitle(R.string.location_perm_title)
                    .setMessage(R.string.location_perm_string)
                    .setPositiveButton(R.string.ok,
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            /* ActivityCompat.requestPermissions(
                                 activity!!,
                                 arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                 MY_PERMISSIONS_REQUEST_LOCATION
                             )*/
                          //  showLocationDialog()

                            //checkLocationPermission()
                        })
                    .create()
                    .show()

            } else { // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
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

    /*override fun onMapReady(map: GoogleMap?) {
        mMap = map ?: return
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.uiSettings.setAllGesturesEnabled(true)
        if (ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.setMyLocationEnabled(true);
        mMap.isMyLocationEnabled
        mMap.setOnMapClickListener(this)
        mMap.setOnInfoWindowClickListener(this)
        mMap.setOnMarkerClickListener(this)

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                === PackageManager.PERMISSION_GRANTED
            ) { //Location Permission already granted
                // buildGoogleApiClient()
                //  mMap.setMyLocationEnabled(true);
            } else { //Request Location Permission
                checkLocationPermission()
            }
        } else {
            checkLocationPermission()
            //  buildGoogleApiClient()
            //  mMap.setMyLocationEnabled(true);
        }
        // setMarkerExample(mMap, 36.833670, 10.234702, "Car Dealer", R.drawable.car_marker); //TODO : eliminate example markers
    }*/

    private fun updateFireBaseUser(uri: Uri?, name: String?) {
        var profileUpdates = UserProfileChangeRequest.Builder().apply {
            if (!name.isNullOrEmpty())
                setDisplayName(name)
            if (uri != null)
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
            if (data.data != null) {

                updateFireBaseUser(data.data!!, null)
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

        if( requestCode == MY_PERMISSIONS_REQUEST_LOCATION)
        {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getCurrentLocation()
            } else {
                Toast.makeText(activity, R.string.permission_denied, Toast.LENGTH_LONG).show()
            }
        }
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
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.

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

