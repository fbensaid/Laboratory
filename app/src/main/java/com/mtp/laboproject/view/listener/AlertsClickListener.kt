package com.mtp.laboproject.view.listener

import android.view.View
import com.mtp.laboproject.data.model.alert.AlertsDetailsResponse


interface AlertsClickListener {
    fun onRecyclerViewItemClick(view: View, labo: AlertsDetailsResponse)

}
