package com.mtp.laboproject.view.ui

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtp.laboproject.R


class DetailsLaboBottomShit : BottomSheetDialogFragment() {

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {

// get the views and attach the listener

        return inflater.inflate(
            R.layout.bottomshit_details_labo, container,
            false
        )

    }

    companion object {
        fun newInstance(): DetailsLaboBottomShit {
            return DetailsLaboBottomShit()
        }
    }
}