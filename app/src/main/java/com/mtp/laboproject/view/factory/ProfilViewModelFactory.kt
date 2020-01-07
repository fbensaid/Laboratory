package com.mtp.laboproject.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mtp.laboproject.view.viewmodel.AuthViewModel
import com.mtp.laboproject.view.viewmodel.LabsViewModel
import com.mtp.laboproject.view.viewmodel.ProfilViewModel

@Suppress("UNCHECKED_CAST")
class ProfilViewModelFactory : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfilViewModel() as T
    }
}