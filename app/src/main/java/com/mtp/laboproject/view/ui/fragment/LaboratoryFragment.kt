package com.mtp.laboproject.view.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
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

class LaboratoryFragment : Fragment(), LaboratoryClickListener {



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
        labsViewModel.getLabs()
        labsViewModel.labsLiveData.observe(viewLifecycleOwner, Observer { laboratory ->
            recycleview_laboratory.also {

                it.layoutManager = GridLayoutManager(requireContext(), 2)
                it.setHasFixedSize(true)
                it.adapter = LaboratoryAdapter(laboratory, this)


            }
        })
    }

    override fun onRecyclerViewItemClick(view: View, labo: LaboratoryListResponse) {
        Log.i("onRecyclerViewItemClick", labo.toString())
        DetailsLaboBottomSheet().show(fragmentManager!!, "tessst")
    }


}

