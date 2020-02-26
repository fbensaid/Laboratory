package com.mtp.laboproject.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.detailsLabsMqtt.DetailsResponse
import com.mtp.laboproject.data.model.labs.LabsObjectResponse
import com.mtp.laboproject.databinding.BottomsheetDetailsLaboBinding
import com.mtp.laboproject.view.viewmodel.DetailsMqttViewModel
import kotlinx.android.synthetic.main.bottomsheet_details_labo.*

class DetailsLaboBottomSheet(private var laboratory: LabsObjectResponse) : BottomSheetDialogFragment() {

    private lateinit var dataBinding: BottomsheetDetailsLaboBinding
    private var mViewModel: DetailsMqttViewModel? = null

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        dataBinding =  DataBindingUtil.inflate(inflater,R.layout.bottomsheet_details_labo, container,false)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAlertList()
        back_btn.setOnClickListener {
            this.dismiss()
        }
    }

    private fun setupAlertList() {
        mViewModel = ViewModelProviders.of(this).get(DetailsMqttViewModel::class.java)
        mViewModel!!.connectToMQQtt(laboratory._id)
        mViewModel!!.detailsLiveData.observe(viewLifecycleOwner, Observer { value: DetailsResponse? ->
                if (value != null) {
                    dataBinding.laboratoryResponseDetailsData=value
                }
            }
        )
    }

    companion object {
        fun newInstance(labo :LabsObjectResponse): DetailsLaboBottomSheet {
            return DetailsLaboBottomSheet(labo)
        }
        const val TAG = "MQTT READER"
    }
}