package com.mtp.laboproject.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mtp.laboproject.R
import com.mtp.laboproject.global.Constants
import kotlinx.android.synthetic.main.fragment_chart.*


class ChartFragment : BaseFragment() {

         private var actualGraphUrl:String=Constants.Urls.GRAPHANA_TEMP

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        settupUrlGraphana()
        settupWebView()

        }

    private fun settupWebView() {
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(actualGraphUrl)
    }

    private fun settupUrlGraphana() {
        cardView1.setOnClickListener { actualGraphUrl=Constants.Urls.GRAPHANA_TEMP
            settupWebView()}
        cardView2.setOnClickListener { actualGraphUrl=Constants.Urls.GRAPHANA_CO2
            settupWebView()}
        cardView3.setOnClickListener { actualGraphUrl=Constants.Urls.GRAPHANA_HUM
            settupWebView()}
        cardView4.setOnClickListener { actualGraphUrl=Constants.Urls.GRAPHANA_VOC
            settupWebView()}

    }

}


