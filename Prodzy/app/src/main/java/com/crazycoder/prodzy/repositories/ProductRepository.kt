package com.crazycoder.prodzy.repositories

import com.crazycoder.prodzy.models.products.ProductsResponseModel
import com.crazycoder.prodzy.services.ProductService
import io.reactivex.Observable

class ProductRepository(private val productService: ProductService) {

    fun getProducts(): Observable<ProductsResponseModel> {
        return productService.getProducts()
    }
}