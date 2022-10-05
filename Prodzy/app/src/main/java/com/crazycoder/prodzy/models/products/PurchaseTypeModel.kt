package com.crazycoder.prodzy.models.products

import com.google.gson.annotations.SerializedName

data class PurchaseTypeModel(
    @SerializedName("purchaseType") var purchaseType: String? = null,
    @SerializedName("displayName") var displayName: String? = null,
    @SerializedName("unitPrice") var unitPrice: Double? = null,
    @SerializedName("minQtyLimit") var minQtyLimit: Int? = null,
    @SerializedName("maxQtyLimit") var maxQtyLimit: Int? = null,
    @SerializedName("cartQty") var cartQty: Int? = null
)
