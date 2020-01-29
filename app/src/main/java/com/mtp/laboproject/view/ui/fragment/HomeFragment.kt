package com.mtp.laboproject.view.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mtp.laboproject.R
import com.mtp.laboproject.databinding.FragmentHomeBinding
import com.mtp.laboproject.view.adapter.LaboratoryAdapter
import com.mtp.laboproject.view.factory.HomeViewModelFactory
import com.mtp.laboproject.view.factory.LabsViewModelFactory
import com.mtp.laboproject.view.viewmodel.HomeViewModel
import com.mtp.laboproject.view.viewmodel.LabsViewModel
import kotlinx.android.synthetic.main.fragment_laboratory.*

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        setHomeDataBinding()
        return homeBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.search_view).isVisible = false
        menu.findItem(R.id.quit_view).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setHomeDataBinding() {
        val factory = HomeViewModelFactory()
        homeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        homeViewModel.getStatistic()
        homeViewModel.statisticsLiveData.observe(viewLifecycleOwner, Observer { homeStatistics ->
            homeBinding.statisticsResponseData=homeStatistics
        })
    }
}
