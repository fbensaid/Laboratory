package com.mtp.laboproject.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mtp.laboproject.view.viewmodel.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel() as T
    }
}