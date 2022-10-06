package com.crazycoder.prodzy.models.products

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("citrusId") var citrusId: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("imageURL") var imageURL: String? = null,
    @SerializedName("price") var price: List<PriceModel> = listOf(),
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("badges") var badges: List<String> = listOf(),
    @SerializedName("ratingCount") var ratingCount: Double? = null,
    @SerializedName("messages") var messages: MessageModel? = MessageModel(),
    @SerializedName("isAddToCartEnable") var isAddToCartEnable: Boolean? = null,
    @SerializedName("addToCartButtonText") var addToCartButtonText: String? = null,
    @SerializedName("isInTrolley") var isInTrolley: Boolean? = null,
    @SerializedName("isInWishlist") var isInWishlist: Boolean? = null,
    @SerializedName("purchaseTypes") var purchaseTypes: List<PurchaseTypeModel> = listOf(),
    @SerializedName("isFindMeEnable") var isFindMeEnable: Boolean? = null,
    @SerializedName("saleUnitPrice") var saleUnitPrice: Double? = null,
    @SerializedName("totalReviewCount") var totalReviewCount: Int? = null,
    @SerializedName("isDeliveryOnly") var isDeliveryOnly: Boolean? = null,
    @SerializedName("isDirectFromSupplier") var isDirectFromSupplier: Boolean? = null,

    @SerializedName("isFavorite") var isFavorite: Boolean = false
)