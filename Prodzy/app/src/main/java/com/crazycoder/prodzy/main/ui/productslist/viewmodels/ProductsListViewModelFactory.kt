package com.crazycoder.prodzy.main.ui.productslist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crazycoder.prodzy.repositories.ProductRepositoryProvider

class ProductsListViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsListViewModel::class.java)) {
            return ProductsListViewModel(
                ProductRepositoryProvider.provideProductRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}