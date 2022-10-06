package com.crazycoder.prodzy.callback

interface ProductFavoritesCallback {
    fun onProductAddOrRemoveFromFavorites(isAdded: Boolean, position: Int)
}