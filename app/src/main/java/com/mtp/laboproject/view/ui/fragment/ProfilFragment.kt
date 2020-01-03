package com.mtp.laboproject.view.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.LaboratoryListResponse
import com.mtp.laboproject.view.adapter.LaboratoryAdapter
import com.mtp.laboproject.listener.LaboratoryClickListener
import com.mtp.laboproject.view.ui.DetailsLaboBottomSheet
import com.mtp.laboproject.view.viewmodel.LabsViewModel
import com.mtp.laboproject.view.factory.LabsViewModelFactory
import kotlinx.android.synthetic.main.fragment_laboratory.*
import android.view.MenuInflater
import android.widget.SearchView


class ProfilFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



}

