package com.mtp.laboproject.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mtp.laboproject.R
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.intentFor
class AuthentificationActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private val TAG = "Authentification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()
        btn_login.setOnClickListener {
            signIn(input_email.text.toString(),input_password.text.toString())
        }

    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(baseContext, "there is a user registred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    startActivity(intentFor<MainActivity>())
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = input_email.text.toString()
        if (TextUtils.isEmpty(email)) {
            input_email.error = "Required."
            valid = false
        } else {
            input_email.error = null
        }

        val password = input_password.text.toString()
        if (TextUtils.isEmpty(password)) {
            input_password.error = "Required."
            valid = false
        } else {
            input_password.error = null
        }

        return valid
    }


}
