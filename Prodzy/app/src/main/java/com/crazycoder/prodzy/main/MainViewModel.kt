package com.crazycoder.prodzy.main

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.base.viewmodels.BaseViewModel
import com.crazycoder.prodzy.models.products.ProductModel
import com.crazycoder.prodzy.models.products.ProductsResponseModel
import com.crazycoder.prodzy.repositories.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    private val _productsResponse = MutableLiveData<ProductsResponseModel>()
    val productsResponse: LiveData<ProductsResponseModel> = _productsResponse
    var favoriteList : MutableList<ProductModel>? = null
    var selectedProduct: ProductModel? = null
    var selectedIndex: Int = -1
    var isApiCalled: Boolean = false


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
                isApiCalled = true
            }, { error ->
                error.printStackTrace()
                _productsResponse.value = ProductsResponseModel(
                    error = R.string.something_went_wrong
                )
            })
    }

    fun addOrRemoveProductFromFavoriteList(productModel: ProductModel) {
        if (productModel.isFavorite) {
            if (favoriteList == null)
                favoriteList = mutableListOf()

            favoriteList!!.add(productModel)
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                favoriteList!!.removeIf { it.id == productModel.id }
            } else {
                favoriteList!!.remove(productModel)
            }
        }
    }
}