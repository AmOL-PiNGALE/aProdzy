package com.crazycoder.prodzy.main.ui.productslist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.base.viewmodels.BaseViewModel
import com.crazycoder.prodzy.models.products.ProductsResponseModel
import com.crazycoder.prodzy.repositories.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductsListViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    private val _productsResponse = MutableLiveData<ProductsResponseModel>()
    val productsResponse: LiveData<ProductsResponseModel> = _productsResponse

    fun getProductList() {
        productRepository.getProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .doOnTerminate { hideLoading() }
            .subscribe({ result ->
                _productsResponse.value = ProductsResponseModel(
                    products = result.products
                )
            }, { error ->
                error.printStackTrace()
                _productsResponse.value = ProductsResponseModel(
                    error = R.string.something_went_wrong
                )
            })
    }
}