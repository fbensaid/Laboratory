package com.mtp.laboproject.view.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.LaboratoryListResponse
import com.mtp.laboproject.view.adapter.LaboratoryAdapter
import com.mtp.laboproject.listener.LaboratoryClickListener
import com.mtp.laboproject.view.ui.DetailsLaboBottomSheet
import com.mtp.laboproject.view.viewmodel.LabsViewModel
import com.mtp.laboproject.view.factory.LabsViewModelFactory
import kotlinx.android.synthetic.main.fragment_laboratory.*
import android.view.MenuInflater
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtp.laboproject.data.model.AlertsDetailsResponse
import com.mtp.laboproject.listener.AlertsClickListener
import com.mtp.laboproject.view.adapter.AlertsAdapter
import com.mtp.laboproject.view.factory.AlertsViewModelFactory
import com.mtp.laboproject.view.viewmodel.AlertsViewModel
import kotlinx.android.synthetic.main.fragment_alert.*


class AlertFragment : BaseFragment(), AlertsClickListener {

    private lateinit var alertsViewModel: AlertsViewModel
    private lateinit var alertsAdapter: AlertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setAlert()
    }

    private fun setAlert() {
        val factory = AlertsViewModelFactory()
        alertsViewModel = ViewModelProviders.of(this, factory).get(AlertsViewModel::class.java)
        alertsViewModel.getAlerts()
        alertsViewModel.alertsLiveData.observe(viewLifecycleOwner, Observer { alert ->
            recycleview_alerts.also {
                it.layoutManager = LinearLayoutManager(context)
                it.setHasFixedSize(true)
               // alertsAdapter = AlertsAdapter(alert, this)
               // it.adapter = alertsAdapter
            }
        })
    }

    override fun onRecyclerViewItemClick(view: View, labo: AlertsDetailsResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

