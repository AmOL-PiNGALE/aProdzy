package com.crazycoder.prodzy.models.products

import com.google.gson.annotations.SerializedName

data class ProductsResponseModel(
    @SerializedName("products") var products: List<ProductModel>? = null,

    @Transient val error: Int? = null
)
