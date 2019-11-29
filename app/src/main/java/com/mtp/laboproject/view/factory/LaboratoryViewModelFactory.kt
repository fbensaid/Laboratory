package com.mtp.laboproject.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mtp.laboproject.data.repository.LaboratoryRepository
import com.mtp.laboproject.view.viewmodel.LaboratoryViewModel

@Suppress("UNCHECKED_CAST")
class LaboratoryViewModelFactory (
    private val repo:LaboratoryRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LaboratoryViewModel(repo) as T
    }

}

