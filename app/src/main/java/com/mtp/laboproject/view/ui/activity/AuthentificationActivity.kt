package com.mtp.laboproject.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mtp.laboproject.LaboApplication.Companion.auth
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.user.UserLoginResponse
import com.mtp.laboproject.global.*
import com.mtp.laboproject.data.remoteApi.Output
import com.mtp.laboproject.global.BiometricPrompt
import com.mtp.laboproject.global.BiometricPromptListener
import com.mtp.laboproject.global.checkBiometric
import com.mtp.laboproject.view.factory.AuthViewModelFactory
import com.mtp.laboproject.view.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.intentFor


class AuthentificationActivity : BaseActivity(), BiometricPromptListener {

    private val TAG = "Authentification"
    private lateinit var biometricPrompt: BiometricPrompt
    private var isFromLoginPassword: Boolean = false
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        biometricPrompt = BiometricPrompt(this, this)

        val factory = AuthViewModelFactory()
        authViewModel = ViewModelProviders.of(this, factory)
            .get(AuthViewModel::class.java)

        //check if user had biometric configuration and support
        checkBiometric()
        //auth with fingerPrint if user connected before

        btn_login.setOnClickListener {
            if ((input_email.validateForm() && input_password.validateForm())) {
                isFromLoginPassword = true
                signIn(input_email.text.toString(), input_password.text.toString())
            }
        }
        cb_display_finger_print.isChecked = authViewModel.getsharedPreference().fingerPrint!!
        authWithFingerPrint()

        cb_display_finger_print.setOnCheckedChangeListener { _, b ->
            if (b && authViewModel.getsharedPreference().isConnectedSuccessBefore) biometricPrompt.authenticateBiometric()
        }

        link_forget_pass.setOnClickListener {
            val value: String = input_email.text.toString() // or just your string
            val intent = Intent(this, ForgottenPasswordActivity::class.java)
            intent.putExtra("email", value)
            startActivity(intent)
        }
    }


    private fun authWithFingerPrint() {
        if (cb_display_finger_print.isChecked && authViewModel.getsharedPreference().isConnectedSuccessBefore) {
            biometricPrompt.authenticateBiometric()
        }
    }

    private fun checkBiometric() {
        when (biometricPrompt.checkBiometricSupport()) {
            checkBiometric.BIOMETRIC_SUCCESS -> ly_display_finger_print.visibility = View.VISIBLE
            else -> ly_display_finger_print.visibility = View.GONE
        }
    }

    private fun storeUserData(user: UserLoginResponse?) {
        authViewModel.storeuser(user!!)
        if (isFromLoginPassword) {
            authViewModel.storeAuthData(
                input_email.text.toString(), input_password.text.toString(),
                true, cb_display_finger_print.isChecked
            )
        }
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Toast.makeText(baseContext, "there is a user registred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(email: String, password: String) {
        /*ct_loading.visibility = View.VISIBLE
        authViewModel.getData(email, password)!!.observe(this, Observer { apiResponse ->
            ct_loading.visibility = View.GONE

            if (apiResponse == null) {
                showSimpleOkDialog(apiResponse!!.error.toString())
                return@Observer
            } else if (apiResponse != null) {
                if (apiResponse.user != null) {
                    if (!apiResponse.user!!.success) { // call is successful
                        Log.i(TAG, "Data response is " + apiResponse.user)
                        showSimpleOkDialog(apiResponse.user!!.error!!.message)

                    } else if (apiResponse!!.user!!.data != null) {
                        storeUserData(apiResponse!!.user!!)
                        startActivity(intentFor<MainActivity>())
                        finish()


                    } else { // call failed.
                        val e: Throwable? = apiResponse.error
                        showSimpleOkDialog(apiResponse.user!!.error!!.message)
                        Toast.makeText(this, "Error is " + e?.message, Toast.LENGTH_SHORT)
                            .show()
                        Log.e(TAG, "Error is " + e?.localizedMessage)
                    }
                } else {
                    showSimpleOkDialog(apiResponse!!.error.toString())
                }*/

        ct_loading.visibility=View.VISIBLE
        authViewModel.setLogin(email,password)
        authViewModel.loginLiveData.observe(this, Observer { userLogin ->
            ct_loading.visibility=View.GONE
            if(userLogin.serverErrorResponse!=null){
                Toast.makeText(this, userLogin.serverErrorResponse!!.message,Toast.LENGTH_LONG).show()
            }else if(userLogin.userLoginResponse!!.success){
                    storeUserData(userLogin.userLoginResponse)
                    startActivity(intentFor<MainActivity>())
                    finish()
            }else {
                Toast.makeText(this, userLogin.userLoginResponse!!.error!!.message,Toast.LENGTH_LONG).show()

            }
        })


    }

    override fun onAuthenticationError() {
        toast("Une Erreur est survenue")
    }

    override fun onAuthenticationSucceeded() {
        if (authViewModel.getsharedPreference().isConnectedSuccessBefore) {
            signIn(
                authViewModel.getsharedPreference().email.toString(),
                authViewModel.getsharedPreference().password.toString()
            )
        } else
            toast("You have to set login and password at first")

    }

    override fun onAuthenticationFailed() {
        toast("Mauvaise identification")
    }

    override fun onNegativeButtonClicked() {
        toast("cancel click")
    }


}
