package com.mtp.laboproject.global

import android.os.Handler
import android.os.Looper
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor


interface BiometricPromptListener {

    fun onAuthenticationError()
    fun onAuthenticationSucceeded()
    fun onAuthenticationFailed()
    fun onNegativeButtonClicked()
}

enum class checkBiometric {
    BIOMETRIC_SUCCESS,
    BIOMETRIC_ERROR_NO_HARDWARE,
    BIOMETRIC_ERROR_HW_UNAVAILABLE,
    BIOMETRIC_ERROR_NONE_ENROLLED
}

class BiometricPrompt(val activity: FragmentActivity, val listener: BiometricPromptListener) {

    private val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Set the title to display.")
        .setSubtitle("Set the subtitle to display.")
        .setDescription("Set the description to display")
        //.setNegativeButtonText("Negative Button")
        .setDeviceCredentialAllowed(true) // allow to authenticate using device PIN, pattern, or password
        .build()

    //private  val executor = Executors.newSingleThreadExecutor()
    private val executor = MainThreadExecutor()

    private val biometricPrompt =
        BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    listener.onNegativeButtonClicked()
                } else {
                    listener.onAuthenticationError()
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onAuthenticationSucceeded()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                listener.onAuthenticationFailed()
            }
        })

    fun authenticateBiometric() {
        biometricPrompt.authenticate(promptInfo)
    }

    fun checkBiometricSupport(): checkBiometric {

        val biometricManager = BiometricManager.from(activity)
        return when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                // App can authenticate using biometrics
                checkBiometric.BIOMETRIC_SUCCESS
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                // No biometric features available on this device
                checkBiometric.BIOMETRIC_ERROR_NO_HARDWARE
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                // Biometric features are currently unavailable
                checkBiometric.BIOMETRIC_ERROR_HW_UNAVAILABLE
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                // The user hasn't associated any biometric credentials with their account
                checkBiometric.BIOMETRIC_ERROR_NONE_ENROLLED
            else -> checkBiometric.BIOMETRIC_SUCCESS
        }
    }
}

class MainThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())

    override fun execute(r: Runnable) {
        handler.post(r)
    }
}