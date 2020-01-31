package com.mtp.laboproject.view.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.mtp.laboproject.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtp.laboproject.data.model.AlertsDetailsResponse
import com.mtp.laboproject.listener.AlertsClickListener
import com.mtp.laboproject.view.adapter.AlertsAdapter
import com.mtp.laboproject.view.factory.AlertsViewModelFactory
import com.mtp.laboproject.view.viewmodel.AlertsViewModel
import kotlinx.android.synthetic.main.fragment_alert.*
import kotlinx.android.synthetic.main.fragment_chart.*


class ChartFragment : BaseFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webview.settings.javaScriptEnabled = true
        webview.loadUrl("http://192.168.2.7:8989/d-solo/IdULMpyWk/iffcharts?orgId=6&from=1580282366460&to=1580303966460&theme=light&panelId=4")
    }
    }


