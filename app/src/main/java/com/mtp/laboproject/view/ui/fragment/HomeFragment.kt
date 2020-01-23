package com.mtp.laboproject.view.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import com.mtp.laboproject.R

class HomeFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.search_view).setVisible(false)
        menu.findItem(R.id.quit_view).setVisible(true)
        super.onCreateOptionsMenu(menu, inflater)
    }



}
