package com.mtp.laboproject.view.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.mtp.laboproject.R
import com.mtp.laboproject.view.factory.ChartViewModelFactory
import com.mtp.laboproject.view.viewmodel.ChartsViewModel
import kotlinx.android.synthetic.main.fragment_chart.*


class ChartFragment : BaseFragment() {
    private lateinit var chartViewModel: ChartsViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

         return inflater.inflate(R.layout.fragment_chart, container, false)

     }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWebViewBinding()
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity!!, R.color.color_primary))
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setOnRefreshListener {
            setWebViewBinding()

            itemsswipetorefresh.isRefreshing = false
        }

    }

    private fun setWebViewBinding() {
        val factory = ChartViewModelFactory()
        val webview : WebView= view!!.findViewById(R.id.webview)
        val progressBar : ProgressBar = view!!.findViewById(R.id.progressBarView)
        val errorImage : ImageView = view!!.findViewById(R.id.errorView)
        chartViewModel = ViewModelProviders.of(this, factory)!!.get(ChartsViewModel::class.java)
        chartViewModel.loadWebView(webview,progressBar,errorImage)
    }


}

