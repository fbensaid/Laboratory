package com.mtp.laboproject.listener

import android.view.View
import com.mtp.laboproject.data.model.LaboratoryListResponse


interface LaboratoryClickListener {
    fun onRecyclerViewItemClick(view: View, labo: LaboratoryListResponse)

}
