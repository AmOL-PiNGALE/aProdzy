package com.crazycoder.prodzy.services

import com.crazycoder.prodzy.models.products.ProductsResponseModel
import com.crazycoder.prodzy.rest.retrofit.ApiUrlFactory
import io.reactivex.Observable
import retrofit2.http.GET

interface ProductService {

    @GET(ApiUrlFactory.ProductListUrl)
    fun getProducts(): Observable<ProductsResponseModel>
}