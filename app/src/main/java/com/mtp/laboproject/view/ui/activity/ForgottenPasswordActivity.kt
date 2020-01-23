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
import com.mtp.laboproject.view.factory.ForgottenPasswordViewModelFactory
import org.jetbrains.anko.intentFor

class ForgottenPasswordActivity : BaseActivity() {


    private lateinit var forgottenPassViewModel: ForgottenPasswordViewModel
    private val TAG = "ForgottenPassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotton_password)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        getSupportActionBar()!!.setLogo(R.drawable.logo_bleu_small);
        getSupportActionBar()!!.setDisplayUseLogoEnabled(true);
        supportActionBar!!.setTitle(" Watcher ")
        handleEmailIntentExtra()

        btn_forgot.setOnClickListener {
            if ((input_email_forgot.validateForm())) {
                setForgotApi(input_email_forgot.text.toString())
            }
        }
    }

    private fun handleEmailIntentExtra() {
        var strEmail=intent.getStringExtra("email")
        var safeEmailText = strEmail ?: " "
        safeEmailText = strEmail ?: "Default Value"
        input_email_forgot.setText(safeEmailText)
    }

    private fun setForgotApi(email: String) {
        val factory = ForgottenPasswordViewModelFactory()
        forgottenPassViewModel = ViewModelProviders.of(this, factory)
            .get(ForgottenPasswordViewModel::class.java)
        forgottenPassViewModel.forgotPassword(email)
        forgottenPassViewModel.forgotPasswordLiveData.observe(this, Observer { forgotResponse ->
            startActivity(intentFor<AuthentificationActivity>())
        })
    }

}
