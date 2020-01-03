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


class LaboratoryFragment : BaseFragment(), LaboratoryClickListener {

    private lateinit var labsViewModel: LabsViewModel
    private lateinit var laboAdapter: LaboratoryAdapter
    private lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_laboratory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRepo()
        setHasOptionsMenu(true)
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
                laboAdapter = LaboratoryAdapter(laboratory, this)
                it.adapter = laboAdapter
                searchLaboratory()
            }
        })
    }

    private fun searchLaboratory() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                laboAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                laboAdapter.filter.filter(query)
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.menu, menu)
        searchView = menu.findItem(R.id.search_view).actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onRecyclerViewItemClick(view: View, labo: LaboratoryListResponse) {
        DetailsLaboBottomSheet().show(fragmentManager!!, "tessst")
    }


}

