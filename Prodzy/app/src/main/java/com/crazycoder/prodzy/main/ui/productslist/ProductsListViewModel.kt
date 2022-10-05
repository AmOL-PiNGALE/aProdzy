package com.crazycoder.prodzy.main.ui.productslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductsListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Product List Fragment"
    }
    val text: LiveData<String> = _text
}