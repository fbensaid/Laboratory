package com.mtp.laboproject.view.adapter

import android.view.View
import com.mtp.laboproject.data.model.LaboratoryResponse


interface LaboratoryClickListener {
    fun onRecyclerViewItemClick(view: View, labo: LaboratoryResponse)

}
