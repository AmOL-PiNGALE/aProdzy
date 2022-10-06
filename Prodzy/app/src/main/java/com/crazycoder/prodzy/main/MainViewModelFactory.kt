package com.crazycoder.prodzy.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crazycoder.prodzy.repositories.ProductRepositoryProvider

class MainViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                ProductRepositoryProvider.provideProductRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}