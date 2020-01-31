package com.mtp.laboproject.view.ui

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.labs.LabsObjectResponse
import com.mtp.laboproject.databinding.BottomsheetDetailsLaboBinding


class DetailsLaboBottomSheet(private var laboratory: LabsObjectResponse) : BottomSheetDialogFragment() {

    private lateinit var dataBinding: BottomsheetDetailsLaboBinding
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        dataBinding =  DataBindingUtil.inflate(inflater,R.layout.bottomsheet_details_labo, container,false)
        dataBinding.laboratoryResponseDetailsData=laboratory
        return dataBinding.root
    }


    companion object {
        fun newInstance(labo :LabsObjectResponse): DetailsLaboBottomSheet {
            return DetailsLaboBottomSheet(labo)
        }
    }
}