package com.mtp.laboproject.listener

import android.view.View
import com.mtp.laboproject.data.model.labs.LabsObjectResponse


interface LaboratoryClickListener {
    fun onRecyclerViewItemClick(view: View, labo: LabsObjectResponse)

}
