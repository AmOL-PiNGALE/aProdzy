package com.crazycoder.prodzy.repositories

import com.crazycoder.prodzy.rest.retrofit.ApiClient
import com.crazycoder.prodzy.services.ProductService

object ProductRepositoryProvider {
    var endpoints : ApiClient = ApiClient()

    fun provideProductRepository(): ProductRepository {
        val productService = endpoints.getClient()!!.create(ProductService::class.java)
        return ProductRepository(productService)
    }
}