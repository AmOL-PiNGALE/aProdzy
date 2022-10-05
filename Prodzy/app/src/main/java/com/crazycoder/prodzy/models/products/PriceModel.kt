package com.crazycoder.prodzy.models.products

import com.google.gson.annotations.SerializedName

data class PriceModel(
    @SerializedName("message") var message: String? = null,
    @SerializedName("value") var value: Double? = null,
    @SerializedName("isOfferPrice") var isOfferPrice: Boolean? = null
)
