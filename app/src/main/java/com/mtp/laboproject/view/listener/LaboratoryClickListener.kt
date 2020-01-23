package com.mtp.laboproject.view.listener

import android.view.View
import com.mtp.laboproject.data.model.labs.LaboratoryResponse


interface LaboratoryClickListener {
    fun onRecyclerViewItemClick(view: View, labo: LaboratoryResponse)

}
