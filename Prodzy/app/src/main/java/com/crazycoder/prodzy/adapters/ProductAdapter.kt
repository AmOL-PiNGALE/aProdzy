package com.crazycoder.prodzy.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.callback.ProductFavoritesCallback
import com.crazycoder.prodzy.main.MainActivity
import com.crazycoder.prodzy.main.ui.productdetails.ProductDetailsFragment
import com.crazycoder.prodzy.models.products.ProductModel
import com.crazycoder.prodzy.utils.DialogUtil
import com.google.gson.Gson

class ProductAdapter(
    private val activity: MainActivity,
    private val products: List<ProductModel>,
    private val callback: ProductFavoritesCallback?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_product, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            holder.bind(products[position], position)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemView: CardView = itemView.findViewById(R.id.cv_item)
        private var productName: AppCompatTextView = itemView.findViewById(R.id.txt_product_name)
        private var imgProduct: AppCompatImageView = itemView.findViewById(R.id.img_product)
        private var productPrice: AppCompatTextView = itemView.findViewById(R.id.txt_product_price)
        private var addButton: AppCompatButton = itemView.findViewById(R.id.btn_add_to_cart)
        private var imgFavorite: AppCompatImageView = itemView.findViewById(R.id.img_favorite)


        fun bind(item: ProductModel, itemPosition: Int) = with(itemView) {
            productName.text = item.title
            val price = item.price[0].value
            productPrice.text = price.toString() + " ₹"

            Glide
                .with(context)
                .load(item.imageURL)
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .into(imgProduct)

            addButton.setOnClickListener { view: View ->
                DialogUtil.showToast(
                    view.context,
                    view.context.getString(R.string.add_to_cart_click)
                )
            }

            setFavoriteIcon(item)

            imgFavorite.setOnClickListener {
                item.isFavorite = !item.isFavorite

                setFavoriteIcon(item)
                activity.viewModel.addOrRemoveProductFromFavoriteList(item)

                val message = if (item.isFavorite)
                    activity.getString(R.string.product_added_to_favorites)
                else
                    activity.getString(R.string.product_remove_from_favorites)
                callback?.onProductAddOrRemoveFromFavorites(item.isFavorite, itemPosition)
                DialogUtil.showToast(activity, message)
            }

            itemView.setOnClickListener { view: View ->
                val productDetailsFragment: Fragment = ProductDetailsFragment()

                val args = Bundle()
                args.putString("itemDetails", Gson().toJson(item))
                productDetailsFragment.arguments = args

                activity.viewModel.selectedIndex = itemPosition

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, productDetailsFragment)
                    .addToBackStack("productDetailsFragment")
                    .commit()
            }
        }

        private fun setFavoriteIcon(productModel: ProductModel) {
            imgFavorite.setImageDrawable(
                if (productModel.isFavorite)
                    AppCompatResources.getDrawable(
                        activity,
                        R.drawable.ic_favorite_black_24
                    )
                else
                    AppCompatResources.getDrawable(
                        activity,
                        R.drawable.ic_favorite_unselected
                    )
            )
        }
    }
}