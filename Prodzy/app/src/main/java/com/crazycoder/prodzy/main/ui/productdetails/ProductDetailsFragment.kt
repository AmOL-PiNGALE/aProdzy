package com.crazycoder.prodzy.main.ui.productdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.databinding.FragmentProductDetailsBinding
import com.crazycoder.prodzy.main.MainActivity
import com.crazycoder.prodzy.models.products.ProductModel
import com.crazycoder.prodzy.utils.DialogUtil
import com.google.gson.Gson

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        initUi()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        if (arguments != null && requireArguments().containsKey("itemDetails")) {
            val productDetails : String? = requireArguments().getString("itemDetails")
            val selectedProduct =  Gson().fromJson(productDetails ?: "", ProductModel::class.java)
            (activity as MainActivity).viewModel.selectedProduct = selectedProduct

            binding.txtProductName.text = selectedProduct.title
            val price = selectedProduct.price[0].value;
            binding.txtProductPrice.text =  price.toString() + " ₹";
            binding.ratingBar.rating = selectedProduct.ratingCount!!.toFloat();

            Glide
                .with(activity as MainActivity)
                .load(selectedProduct.imageURL)
                .placeholder(R.drawable.placeholder_image)
                .fitCenter()
                .into(binding.imgProduct);

            setFavoriteIcon()

            binding.imgFavorite.setOnClickListener {
                selectedProduct.isFavorite = !selectedProduct.isFavorite

                setFavoriteIcon()
                (activity as MainActivity).viewModel.addOrRemoveProductFromFavoriteList(selectedProduct)

                (activity as MainActivity).viewModel.productsResponse
                    .value?.products?.get((activity as MainActivity).viewModel.selectedIndex)?.isFavorite = selectedProduct.isFavorite

                val message = if (selectedProduct.isFavorite)
                    getString(R.string.product_added_to_favorites) else getString(R.string.product_remove_from_favorites)
                DialogUtil.showToast(activity as MainActivity, message)
            }

            binding.btnAddToCart.setOnClickListener { view: View ->
                DialogUtil.showToast(view.context, view.context.getString(R.string.add_to_cart_click))
            }
        } else {
            binding.clEmptyView.visibility = View.VISIBLE
            binding.btnAddToCart.visibility = View.GONE
            binding.clProductDetails.visibility = View.GONE
            binding.imgProduct.visibility = View.GONE
        }
    }

    private fun setFavoriteIcon() {
        binding.imgFavorite.setImageDrawable(
            if ((activity as MainActivity).viewModel.selectedProduct != null &&
                (activity as MainActivity).viewModel.selectedProduct!!.isFavorite)
                AppCompatResources.getDrawable(
                    activity as MainActivity,
                    R.drawable.ic_favorite_black_24
                )
            else
                AppCompatResources.getDrawable(
                    activity as MainActivity,
                    R.drawable.ic_favorite_unselected
                ))
    }
}