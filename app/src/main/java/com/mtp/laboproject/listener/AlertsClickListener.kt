package com.mtp.laboproject.listener

import android.view.View
import com.mtp.laboproject.data.model.AlertsDetailsResponse


interface AlertsClickListener {
    fun onRecyclerViewItemClick(view: View, labo: AlertsDetailsResponse)

}
