package com.mtp.laboproject.listener

import android.view.View
import com.mtp.laboproject.data.model.labs.LaboratoryResponse


interface LaboratoryClickListener {
    fun onRecyclerViewItemClick(view: View, labo: LaboratoryResponse)

}
