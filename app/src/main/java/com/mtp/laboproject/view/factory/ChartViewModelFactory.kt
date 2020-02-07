package com.mtp.laboproject.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mtp.laboproject.view.viewmodel.AlertsViewModel
import com.mtp.laboproject.view.viewmodel.ChartsViewModel
import com.mtp.laboproject.view.viewmodel.HomeViewModel
import com.mtp.laboproject.view.viewmodel.LabsViewModel

@Suppress("UNCHECKED_CAST")
class ChartViewModelFactory : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChartsViewModel() as T
    }
}