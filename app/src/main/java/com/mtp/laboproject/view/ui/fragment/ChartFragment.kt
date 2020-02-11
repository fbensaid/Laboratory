package com.mtp.laboproject.view.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hlab.fabrevealmenu.enums.Direction
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener
import com.hlab.fabrevealmenu.model.FABMenuItem
import com.hlab.fabrevealmenu.view.FABRevealMenu
import com.mtp.laboproject.R
import com.mtp.laboproject.global.Constants
import com.mtp.laboproject.view.factory.ChartViewModelFactory
import com.mtp.laboproject.view.viewmodel.ChartsViewModel
import kotlinx.android.synthetic.main.fragment_chart.*
import java.util.*


class ChartFragment : BaseFragment() , OnFABMenuSelectedListener {
    private lateinit var chartViewModel: ChartsViewModel
    private var items: ArrayList<FABMenuItem>? = null
    private var actualGraphUrl: String = Constants.Urls.GRAPHANA_TEMP

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_chart, container, false)

    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItems(false)
        setFloatingButtonActions()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWebViewBinding()
        setSwipeRefresh()
        setFloatingButtonActions()
    }

    private fun setFloatingButtonActions() {

        val fab: FloatingActionButton = view!!.findViewById(R.id.fab)
        val fabMenu: FABRevealMenu = view!!.findViewById(R.id.fabMenu)

        try {
            if (fab != null && fabMenu != null) {
                setFabMenu(fabMenu)
                fabMenu.setMenuItems(items)
                fabMenu.bindAnchorView(fab)
                fabMenu.menuDirection = Direction.UP
                fabMenu.setOverlayBackground(R.color.transparent)
                fabMenu.setNormalMenu()
                fabMenu.setShowOverlay(false)
                fabMenu.setOnFABMenuSelectedListener(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setSwipeRefresh() {
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                activity!!,
                R.color.color_primary
            )
        )
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setOnRefreshListener {
            setWebViewBinding()

            itemsswipetorefresh.isRefreshing = false
        }
    }

    private fun setWebViewBinding() {
        val factory = ChartViewModelFactory()
        val webview: WebView = view!!.findViewById(R.id.webview)
        val progressBar: ProgressBar = view!!.findViewById(R.id.progressBarView)
        val errorImage: ImageView = view!!.findViewById(R.id.errorView)
        chartViewModel = ViewModelProviders.of(this, factory)!!.get(ChartsViewModel::class.java)
        chartViewModel.loadWebView(webview, progressBar, errorImage)

        //settupUrlGraphana()
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

    /*private fun settupUrlGraphana() {
        cardView1.setOnClickListener { actualGraphUrl=Constants.Urls.GRAPHANA_TEMP
            settupWebView()}
        cardView2.setOnClickListener { actualGraphUrl=Constants.Urls.GRAPHANA_CO2
            settupWebView()}
        cardView3.setOnClickListener { actualGraphUrl=Constants.Urls.GRAPHANA_HUM
            settupWebView()}
        cardView4.setOnClickListener { actualGraphUrl=Constants.Urls.GRAPHANA_VOC
            settupWebView()}

    }*/
    private  fun initItems(toShowDoubleItems: Boolean): Unit {
        items = ArrayList()
        items!!.add(
            FABMenuItem(
                "Humidity",
                AppCompatResources.getDrawable(activity!!, R.drawable.humidity_chart_ic)
            )
        )
        items!!.add(
            FABMenuItem(
                "VOC",
                AppCompatResources.getDrawable(activity!!, R.drawable.alcohol_chart_ic)
            )
        )
        items!!.add(
            FABMenuItem(
                "CO2",
                AppCompatResources.getDrawable(activity!!, R.drawable.co2_chart_ic)
            )
        )
        items!!.add(
            FABMenuItem(
                "Temperature",
                AppCompatResources.getDrawable(activity!!, R.drawable.temp_chart_ic)
            )
        )

    }

    override fun onMenuItemSelected(view: View?, id: Int) {
        if (id >= 0 && items != null && items!!.size > id) {
            Toast.makeText(
                activity,
                items!!.get(id)!!.title,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}




