package com.mtp.laboproject.view.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.farouk.travelcar.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.UserResponse
import com.mtp.laboproject.global.BiometricPrompt
import com.mtp.laboproject.global.BiometricPromptListener
import com.mtp.laboproject.global.SharedPreferences
import com.mtp.laboproject.global.checkBiometric
import kotlinx.android.synthetic.main.activity_auth.*
import net.simplifiedcoding.mvvmsampleapp.util.toast
import net.simplifiedcoding.mvvmsampleapp.util.validateForm
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class AuthentificationActivity : AppCompatActivity(), BiometricPromptListener {


    private lateinit var auth: FirebaseAuth
    private val TAG = "Authentification"
    private  lateinit var biometricPrompt:BiometricPrompt
    private var isFromLoginPassword: Boolean = false

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        LaboApplication.appComponent.inject(this)
        biometricPrompt = BiometricPrompt(this, this)
        auth = FirebaseAuth.getInstance()

        //check if user had biometric configuration and support
        checkBiometric()
        //auth with fingerPrint if user connected before

        btn_login.setOnClickListener {
            if ((input_email.validateForm() && input_password.validateForm())) {
                isFromLoginPassword=true
                signIn(input_email.text.toString(),input_password.text.toString())
            }
        }
        cb_display_finger_print.isChecked= sharedPreferences.fingerPrint!!
        authWithFingerPrint()

        cb_display_finger_print.setOnCheckedChangeListener { _, b ->
            if(b && sharedPreferences.isConnectedSuccess) biometricPrompt.authenticateBiometric()
        }
    }
    private fun authWithFingerPrint() {
        if(cb_display_finger_print.isChecked&&sharedPreferences.isConnectedSuccess){
            biometricPrompt.authenticateBiometric()
        }
    }
        private fun checkBiometric(){
        when (biometricPrompt.checkBiometricSupport()) {
            checkBiometric.BIOMETRIC_SUCCESS -> ly_display_finger_print.visibility=View.VISIBLE
            else -> ly_display_finger_print.visibility=View.GONE
        }
    }
    private fun storeUserData(user:FirebaseUser?) {
        sharedPreferences.userResponse= UserResponse(user!!.displayName,user!!.email,user.photoUrl.toString(),user.uid)
        if(isFromLoginPassword){
            sharedPreferences.email=input_email.text.toString()
            sharedPreferences.password=input_password.text.toString()
            sharedPreferences.isConnectedSuccess=true
            sharedPreferences.fingerPrint=cb_display_finger_print.isChecked
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
        ct_loading.visibility=View.VISIBLE
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    storeUserData(user)
                    startActivity(intentFor<MainActivity>())
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    ct_loading.visibility=View.GONE

                }
            }
    }


    override fun onAuthenticationError() {
        toast("Une Erreur est survenue")
    }

    override fun onAuthenticationSucceeded() {
        if(sharedPreferences.isConnectedSuccess) {
            signIn(sharedPreferences.email.toString(),sharedPreferences.password.toString())
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
