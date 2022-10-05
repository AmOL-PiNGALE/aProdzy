package com.crazycoder.prodzy.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.main.MainActivity
import com.crazycoder.prodzy.main.ui.productdetails.ProductDetailsFragment
import com.crazycoder.prodzy.models.products.ProductModel
import com.crazycoder.prodzy.utils.DialogUtil
import com.google.gson.Gson

class ProductAdapter(private val products: List<ProductModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_product, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemView: CardView = itemView.findViewById(R.id.cv_item)
        private var productName: AppCompatTextView = itemView.findViewById(R.id.txt_product_name)
        private var imgProduct: AppCompatImageView = itemView.findViewById(R.id.img_product)
        private var productPrice: AppCompatTextView = itemView.findViewById(R.id.txt_product_price)
        private var addButton : AppCompatButton = itemView.findViewById(R.id.btn_add_to_cart)


        fun bind(item: ProductModel) = with(itemView) {
            productName.text = item.title
            val price = item.price[0].value;
            productPrice.text =  price.toString() + " ₹";

            Glide
                .with(context)
                .load(item.imageURL)
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .into(imgProduct);

            addButton.setOnClickListener { view: View ->
                DialogUtil.showToast(view.context, view.context.getString(R.string.add_to_cart_click))
            }

            itemView.setOnClickListener  { view: View ->
                val activity = view.context as MainActivity
                val productDetailsFragment: Fragment = ProductDetailsFragment()

                val args = Bundle()
                args.putString("itemDetails", Gson().toJson(item))
                productDetailsFragment.arguments = args

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, productDetailsFragment)
                    .addToBackStack("productDetailsFragment")
                    .commit()
            }
        }
    }
}