package com.mtp.laboproject.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mtp.laboproject.R
import com.mtp.laboproject.view.factory.LaboratoryViewModelFactory
import com.mtp.laboproject.view.viewmodel.LaboratoryViewModel

class HomeFragment : Fragment() {


    private lateinit var factory: LaboratoryViewModelFactory
    private lateinit var viewModel: LaboratoryViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}
