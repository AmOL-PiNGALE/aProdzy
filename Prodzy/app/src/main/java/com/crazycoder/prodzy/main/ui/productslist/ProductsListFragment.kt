package com.crazycoder.prodzy.main.ui.productslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.adapters.ProductAdapter
import com.crazycoder.prodzy.databinding.FragmentProductsListBinding
import com.crazycoder.prodzy.main.MainActivity
import com.crazycoder.prodzy.models.products.ProductModel
import com.crazycoder.prodzy.utils.DialogUtil

class ProductsListFragment : Fragment() {

    private var _binding: FragmentProductsListBinding? = null

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private var productsList: List<ProductModel>? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        if (!(activity as MainActivity).viewModel.isApiCalled) {
            (activity as MainActivity).viewModel.getProductList()
        }

        (activity as MainActivity).viewModel.productsResponse.observe(viewLifecycleOwner, Observer {
            val productsResponse = it ?: return@Observer
            if (productsResponse.error != null) {
                DialogUtil.showToast(this.requireContext(), getString(productsResponse.error))
            } else {
                if (productsResponse.products != null && productsResponse.products!!.isNotEmpty()) {
                    productsList = productsResponse.products

                    layoutManager = LinearLayoutManager(this.context)
                    binding.rvProductList.layoutManager = layoutManager

                    adapter =
                        ProductAdapter(activity as MainActivity, productsResponse.products!!, null)
                    binding.rvProductList.adapter = adapter
                } else {
                    DialogUtil.showToast(
                        activity as MainActivity,
                        getString(R.string.no_products_present_message)
                    )
                    binding.clEmptyView.visibility = View.VISIBLE
                    binding.rvProductList.visibility = View.GONE
                }
            }
        })
    }
}