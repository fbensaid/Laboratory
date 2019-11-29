package com.mtp.laboproject.view.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.LaboratoryResponse
import com.mtp.laboproject.data.remoteApi.ApiInterface
import com.mtp.laboproject.data.repository.LaboratoryRepository
import com.mtp.laboproject.data.rest.LaboratoryDetailsResponse
import com.mtp.laboproject.view.adapter.LaboratoryAdapter
import com.mtp.laboproject.view.adapter.LaboratoryClickListener
import com.mtp.laboproject.view.factory.LaboratoryViewModelFactory
import com.mtp.laboproject.view.viewmodel.LabsViewModel
import com.mtp.laboproject.view.viewmodel.LaboratoryViewModel
import com.mtp.laboproject.view.viewmodel.LabsViewModelFactory

import kotlinx.android.synthetic.main.fragment_laboratory.*

class LaboratoryFragment : Fragment() , LaboratoryClickListener {




    // private lateinit var factory: LabsViewModelFactory
    private lateinit var viewModel: LaboratoryViewModel
    private lateinit var labsViewModel: LabsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_laboratory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //setupRepo()
        setRepo()
    }

    private fun setRepo() {

        val factory = LabsViewModelFactory()
        labsViewModel = ViewModelProviders.of(this, factory)
            .get(LabsViewModel::class.java)
        //get latest news from view model

        labsViewModel.getLabs()
        //observe viewModel live data
        labsViewModel.labsLiveData.observe(viewLifecycleOwner, Observer { laboratory ->
            recycleview_laboratory.also {

                it.layoutManager = GridLayoutManager(requireContext(), 2)
                it.setHasFixedSize(true)
                it.adapter = LaboratoryAdapter(laboratory,this)
                it.layoutManager =LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter= LaboratoryAdapter(laboratory,this)

            }
        })
    }
    override fun onRecyclerViewItemClick(view: View, labo: LaboratoryResponse) {
        Log.i("onRecyclerViewItemClick",labo.toString())
        DetailsLaboBottomShit().show(fragmentManager!!,"tessst")
    }


/*private fun setupRepo() {
    val api = ApiInterface()
    val repository = LaboratoryRepository(api)
    factory = LaboratoryViewModelFactory(repository)
    viewModel = ViewModelProviders.of(this, factory).get(LaboratoryViewModel::class.java)
    viewModel.getLaboratory()
    viewModel.laboratory.observe(viewLifecycleOwner, Observer { laboratory ->
        recycleview_laboratory.also {
            it.layoutManager = GridLayoutManager(requireContext(), 2)
            it.setHasFixedSize(true)
            it.adapter = LaboratoryAdapter(laboratory)
        }
    })


}*/


}

