package com.mtp.laboproject.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mtp.laboproject.view.viewmodel.ForgottenPasswordViewModel
import com.mtp.laboproject.view.viewmodel.LabsViewModel

@Suppress("UNCHECKED_CAST")
class ForgottenPasswordViewModelFactory : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForgottenPasswordViewModel() as T
    }
}