package com.mtp.laboproject.view.viewmodel

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import com.mtp.laboproject.global.Constants


class ChartsViewModel() : ViewModel() {
    private var hideProgress: Boolean = false
    var timeout = false
    private lateinit var progressBarView: ProgressBar
    private lateinit var webViewLay: WebView
    private lateinit var errorView: ImageView

    private inner class Client : WebViewClient() {
        override fun onReceivedError(
            view: WebView, request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            hideProgress = true
            progressBarView.setVisibility(View.GONE)
            webViewLay.setVisibility(View.GONE)
            errorView.setVisibility(View.VISIBLE)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            hideProgress = true
            progressBarView.setVisibility(View.GONE)
            errorView.setVisibility(View.VISIBLE)
            webViewLay.setVisibility(View.GONE)
            timeout = false
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            Thread(Runnable {
                timeout = true
                try {
                    Thread.sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                if (timeout) {

                    Handler(Looper.getMainLooper()).post(Runnable {
                        webViewLay.setVisibility(View.GONE)
                        errorView.setVisibility(View.VISIBLE)
                        progressBarView.setVisibility(View.GONE)
                    })
                    // do what you want
                    // Toast.makeText(getContext(),"Error in loading graph",Toast.LENGTH_LONG).show()
                }
            }).start()
        }
    }

    fun loadWebView(webView: WebView, progressBar: ProgressBar, image: ImageView) {

        webViewLay = webView
        errorView = image
        progressBarView = progressBar
        webView.setWebViewClient(webViewClient)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(Constants.Urls.HUMIDITY_URL)
    }


    val webViewClient: WebViewClient
        get() = Client()

    /*@get:Bindable
    var isHideProgress: Boolean
        get() = hideProgress
        private set(hideProgress) {
            //hideProgress = hideProgress
            //notifyPropertyChanged(javax.swing.text.html.HTML.Tag.BR.hideProgress)
        }*/


}