package com.mtp.laboproject.view.ui.activity

import android.os.Bundle
import android.os.Handler
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.R
import com.mtp.laboproject.global.Constants.Variants.SPLASHTIME
import org.jetbrains.anko.intentFor

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({ openMain() }, SPLASHTIME)
    }

    private fun openMain() {
       if (!LaboApplication.appComponent.getPreferences().isStillConnected)
            startActivity(intentFor<AuthentificationActivity>())
         else
            startActivity(intentFor<MainActivity>())

        finish()
    }
}