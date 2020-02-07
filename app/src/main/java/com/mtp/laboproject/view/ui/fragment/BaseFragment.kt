package com.mtp.laboproject.view.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


abstract class BaseFragment : Fragment() {

    companion object {
        val TAG: String = BaseFragment::class.java.simpleName
    }


    var mFragmentNavigation: FragmentNavigation? = null

    fun setFragmentNavigation(fragmentNavigation: FragmentNavigation) {
        mFragmentNavigation = fragmentNavigation
    }

    interface FragmentNavigation {
        fun pushFragment(fragment: BaseFragment, fragNavTransactionOptions: FragmentTransaction? = null)
        fun replaceFragment(fragment: BaseFragment, fragNavTransactionOptions: FragmentTransaction? = null)
        fun setFragmentTitle(title: String)
        fun setToolbarResearchButton()
        fun setToolbarShareButton()

    }


}
