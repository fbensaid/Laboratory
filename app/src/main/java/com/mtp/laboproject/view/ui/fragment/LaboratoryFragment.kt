package com.mtp.laboproject.view.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.LaboratoryListResponse
import com.mtp.laboproject.listener.LaboratoryClickListener
import com.mtp.laboproject.view.adapter.LaboratoryAdapter
import com.mtp.laboproject.view.factory.LabsViewModelFactory
import com.mtp.laboproject.view.ui.DetailsLaboBottomSheet
import com.mtp.laboproject.view.viewmodel.LabsViewModel
import kotlinx.android.synthetic.main.fragment_laboratory.*

class LaboratoryFragment : BaseFragment(), LaboratoryClickListener, View.OnClickListener {

    private lateinit var labsViewModel: LabsViewModel
    private lateinit var laboAdapter: LaboratoryAdapter
    private lateinit var searchView: SearchView
    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var mBottomSheetBehaviourMarkerInfos: BottomSheetBehavior<*>? = null


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
     //  setBottomSheetBehaviour()
        setBottomSheetMarkerBehaviour()
    }

    private fun setRepo() {
        val factory = LabsViewModelFactory()
        labsViewModel = ViewModelProviders.of(this, factory).get(LabsViewModel::class.java)
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

    private fun setBottomSheetBehaviour() {
        mBottomSheetBehavior = BottomSheetBehavior.from<View>(bottom_sheet_laboratory)
        (mBottomSheetBehavior as BottomSheetBehavior<View>).setBottomSheetCallback(object :
            BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    (mBottomSheetBehavior as BottomSheetBehavior<View>).setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        bottom_sheet_laboratory.setOnClickListener(this)
    }

    private fun setBottomSheetMarkerBehaviour() {
        mBottomSheetBehaviourMarkerInfos =
            BottomSheetBehavior.from<View>(bottom_sheet_laboratory)
        BottomSheetBehavior.STATE_EXPANDED
        (mBottomSheetBehaviourMarkerInfos as BottomSheetBehavior<View>).setBottomSheetCallback(
            object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        (mBottomSheetBehaviourMarkerInfos as BottomSheetBehavior<View>).setState(
                            BottomSheetBehavior.STATE_EXPANDED
                        )
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {

                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        bottom_sheet_laboratory.setOnClickListener(this)
        (mBottomSheetBehaviourMarkerInfos as BottomSheetBehavior<View>).setState(BottomSheetBehavior.STATE_EXPANDED)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

