package com.mtp.laboproject.view.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mtp.laboproject.R
import com.mtp.laboproject.view.adapter.LaboratoryAdapter
import com.mtp.laboproject.view.factory.LabsViewModelFactory
import com.mtp.laboproject.view.viewmodel.ForgottenPasswordViewModel
import com.mtp.laboproject.view.viewmodel.LabsViewModel
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_auth.btn_login
import kotlinx.android.synthetic.main.activity_forgotton_password.*
import kotlinx.android.synthetic.main.fragment_laboratory.*
import net.simplifiedcoding.mvvmsampleapp.util.validateForm
import org.jetbrains.anko.intentFor
class ForgottenPasswordActivity : BaseActivity() {


    private lateinit var forgottenPassViewModel: ForgottenPasswordViewModel
    private lateinit var auth: FirebaseAuth
    private val TAG = "ForgottenPassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotton_password)



        btn_login.setOnClickListener {
            if ((input_email.validateForm())) {
                setForgotApi(input_email_forgot.text.toString())
            }
        }

    }

    private fun setForgotApi(email : String) {
        val factory = LabsViewModelFactory()
        forgottenPassViewModel = ViewModelProviders.of(this, factory)
            .get(ForgottenPasswordViewModel::class.java)
        forgottenPassViewModel.forgotPassword(email)
        forgottenPassViewModel.forgotPasswordLiveData.observe(this, Observer { forgotResponse ->
            startActivity(intentFor<AuthentificationActivity>())
        })
    }



    // unused function ( fun EditText.validateForm() in ViewUtils)
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
