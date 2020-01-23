package com.mtp.laboproject.view.listener

import android.view.View
import com.mtp.laboproject.data.model.AlertsDetailsResponse


interface AlertsClickListener {
    fun onRecyclerViewItemClick(view: View, labo: AlertsDetailsResponse)

}
