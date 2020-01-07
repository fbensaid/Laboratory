package com.mtp.laboproject.view.ui.activity

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.AlertsDetailsResponse
import com.mtp.laboproject.listener.AlertsClickListener
import com.mtp.laboproject.view.adapter.AlertsAdapter
import com.mtp.laboproject.view.factory.AlertsViewModelFactory
import com.mtp.laboproject.view.ui.DetailsLaboBottomSheet
import com.mtp.laboproject.view.viewmodel.AlertsViewModel
import kotlinx.android.synthetic.main.alerts_activity.*

class AlertsActivity : BaseActivity(), AlertsClickListener {

    private lateinit var alertsViewModel: AlertsViewModel
    private lateinit var alertsAdapter: AlertsAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alerts_activity)

        //setRepo()

    }
    // delete this because we dont need viewLifecycleOwner in activity
//    private fun setRepo() {
//        val factory = AlertsViewModelFactory()
//        alertsViewModel = ViewModelProviders.of(this, factory)
//            .get(AlertsViewModel::class.java)
//        alertsViewModel.getAlerts()
//        alertsViewModel.alertsLiveData.observe(viewLifecycleOwner, Observer { alerts ->
//            recycleview_alerts.also {
//                it.layoutManager = GridLayoutManager(this, 2)
//                it.setHasFixedSize(true)
//                alertsAdapter = AlertsAdapter(alerts, this)
//                it.adapter = alertsAdapter
//                searchAlerts()
//            }
//        })
//    }

    private fun searchAlerts() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                alertsAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                alertsAdapter.filter.filter(query)
                return false
            }
        })
    }

    // onCreateOptionsMenu()
// Create options menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        searchView = menu.findItem(R.id.search_view).actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        super.onCreateOptionsMenu(menu)
        return true
    }

    // onOptionsItemSelected()
// "On click listener" for options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }

    override fun onRecyclerViewItemClick(view: View, labo: AlertsDetailsResponse) {
       // DetailsLaboBottomSheet().show(fragmentManager!!, "tessst")
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}


