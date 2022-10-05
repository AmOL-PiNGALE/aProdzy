package com.crazycoder.prodzy.models.products

import com.google.gson.annotations.SerializedName

data class MessageModel(
    @SerializedName("secondaryMessage") var secondaryMessage: String? = null,
    @SerializedName("sash") var sash: Any? = Any()
)
