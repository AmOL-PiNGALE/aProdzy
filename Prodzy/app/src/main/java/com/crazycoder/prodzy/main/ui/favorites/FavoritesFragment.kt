package com.crazycoder.prodzy.main.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.adapters.ProductAdapter
import com.crazycoder.prodzy.callback.ProductFavoritesCallback
import com.crazycoder.prodzy.databinding.FragmentFavoritesBinding
import com.crazycoder.prodzy.main.MainActivity
import com.crazycoder.prodzy.models.products.ProductModel
import com.crazycoder.prodzy.utils.DialogUtil

class FavoritesFragment : Fragment(), ProductFavoritesCallback {

    private var _binding: FragmentFavoritesBinding? = null

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private var productsList: List<ProductModel>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        initUi()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        (activity as MainActivity).actionBar?.title = getString(R.string.title_favorites)

        if ((activity as MainActivity).viewModel.favoriteList != null) {
            productsList = (activity as MainActivity).viewModel.favoriteList

            layoutManager = LinearLayoutManager(this.context)
            binding.rvFavoritesList.layoutManager = layoutManager

            adapter = ProductAdapter(activity as MainActivity, productsList!!, this)
            binding.rvFavoritesList.adapter = adapter
            binding.rvFavoritesList.visibility = View.VISIBLE
            binding.clEmptyView.visibility = View.GONE
        } else {
            DialogUtil.showToast(
                activity as MainActivity,
                getString(R.string.no_favorites_products_present_message)
            )
            setEmptyView()
        }
    }

    private fun setEmptyView() {
        binding.clEmptyView.visibility = View.VISIBLE
        binding.rvFavoritesList.visibility = View.GONE
    }

    override fun onProductAddOrRemoveFromFavorites(isAdded: Boolean, position: Int) {
        if (isAdded) {
            adapter.notifyItemInserted(position)
        } else {
            adapter.notifyItemRemoved(position)
        }
        if (adapter.itemCount <= 0) {
            setEmptyView()
        }
    }
}