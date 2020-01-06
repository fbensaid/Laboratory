package com.mtp.laboproject.listener

import android.view.View
import com.mtp.laboproject.data.model.AlertsDetailsResponse
import com.mtp.laboproject.data.model.LaboratoryListResponse


interface AlertsClickListener {
    fun onRecyclerViewItemClick(view: View, labo: AlertsDetailsResponse)

}
