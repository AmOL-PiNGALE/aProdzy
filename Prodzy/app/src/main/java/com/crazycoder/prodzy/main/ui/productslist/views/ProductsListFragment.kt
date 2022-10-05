package com.crazycoder.prodzy.main.ui.productslist.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.adapters.ProductAdapter
import com.crazycoder.prodzy.databinding.FragmentProductsListBinding
import com.crazycoder.prodzy.main.MainActivity
import com.crazycoder.prodzy.main.ui.productslist.viewmodels.ProductsListViewModel
import com.crazycoder.prodzy.main.ui.productslist.viewmodels.ProductsListViewModelFactory
import com.crazycoder.prodzy.models.products.ProductModel
import com.crazycoder.prodzy.utils.DialogUtil

class ProductsListFragment : Fragment() {

    private var _binding: FragmentProductsListBinding? = null
    private lateinit var productsListViewModel: ProductsListViewModel

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
        productsListViewModel = ViewModelProvider(
            activity as ViewModelStoreOwner,
           ProductsListViewModelFactory()
        ).get(ProductsListViewModel::class.java)

        _binding = FragmentProductsListBinding.inflate(inflater, container, false)

        initUi()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {

        (activity as MainActivity).actionBar?.title = getString(R.string.title_products_list)

        productsListViewModel.getProductList()

        layoutManager = LinearLayoutManager(this.context)
        binding.rvProductList.layoutManager = layoutManager

        productsListViewModel.productsResponse.observe(viewLifecycleOwner, Observer {
            val productsResponse = it ?: return@Observer
            if (productsResponse.error != null) {
                DialogUtil.showToast(this.requireContext(), getString(productsResponse.error))
            } else {
                if (productsResponse.products != null && productsResponse.products!!.isNotEmpty()) {
                    productsList = productsResponse.products
                    adapter = ProductAdapter(productsResponse.products!!)
                    binding.rvProductList.adapter = adapter
                } else {
                    DialogUtil.showToast(activity as MainActivity, getString(R.string.no_products_present_message));
                    binding.clEmptyView.visibility = View.VISIBLE
                    binding.rvProductList.visibility = View.GONE
                }
            }
        })
    }
}