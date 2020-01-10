package com.mtp.laboproject.view.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mtp.laboproject.R
import com.mtp.laboproject.view.factory.LabsViewModelFactory
import com.mtp.laboproject.view.viewmodel.ForgottenPasswordViewModel
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_auth.btn_login
import kotlinx.android.synthetic.main.activity_forgotton_password.*
import com.mtp.laboproject.global.validateForm
import org.jetbrains.anko.intentFor
class ForgottenPasswordActivity : BaseActivity() {


    private lateinit var forgottenPassViewModel: ForgottenPasswordViewModel
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

}
