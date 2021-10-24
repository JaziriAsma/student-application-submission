package com.android.internal.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.internal.test.repository.Repository
//Création d'une instance de ViewModel
class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }


}