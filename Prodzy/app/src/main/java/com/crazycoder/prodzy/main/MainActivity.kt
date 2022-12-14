package com.crazycoder.prodzy.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.base.views.BaseActivity
import com.crazycoder.prodzy.databinding.ActivityMainBinding
import com.crazycoder.prodzy.main.ui.favorites.FavoritesFragment
import com.crazycoder.prodzy.main.ui.productslist.ProductsListFragment

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this as ViewModelStoreOwner,
            MainViewModelFactory()
        ).get(MainViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        this.setProgressBar(binding.loadingProgressBar)

        setCurrentFragment(ProductsListFragment(), "ProductsListFragment")

        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_products_list -> setCurrentFragment(
                    ProductsListFragment(),
                    "ProductsListFragment"
                )
                R.id.navigation_favorites -> setCurrentFragment(
                    FavoritesFragment(),
                    "FavoritesFragment"
                )
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }
}