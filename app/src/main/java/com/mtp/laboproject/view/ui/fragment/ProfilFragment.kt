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
import com.mtp.laboproject.view.factory.AuthViewModelFactory
import com.mtp.laboproject.view.factory.ProfilViewModelFactory
import com.mtp.laboproject.view.ui.activity.AuthentificationActivity
import com.mtp.laboproject.view.ui.activity.MainActivity
import com.mtp.laboproject.view.viewmodel.AuthViewModel
import com.mtp.laboproject.view.viewmodel.ProfilViewModel
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_profil.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor


class ProfilFragment : BaseFragment() {

    private lateinit var profilViewModel: ProfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ProfilViewModelFactory()
        profilViewModel = ViewModelProviders.of(this, factory).get(ProfilViewModel::class.java)

        setupUserInfo()

        switch_finger.setOnCheckedChangeListener { _, b ->
            profilViewModel.getsharedPreference().fingerPrint=b
        }
        switch_notif.setOnCheckedChangeListener { _, b ->
            profilViewModel.getsharedPreference().notification=b
        }
        btn_disconnect.setOnClickListener {
            profilViewModel.getsharedPreference().isStillConnected=false
            startActivity(intentFor<AuthentificationActivity>())
            //this.finish()
        }
    }

    private fun setupUserInfo() {
        switch_finger.isChecked= profilViewModel.getsharedPreference().fingerPrint!!
        switch_notif.isChecked= profilViewModel.getsharedPreference().notification!!

    }


}

