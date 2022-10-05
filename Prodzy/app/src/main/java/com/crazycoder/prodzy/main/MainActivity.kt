package com.crazycoder.prodzy.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.crazycoder.prodzy.R
import com.crazycoder.prodzy.base.views.BaseActivity
import com.crazycoder.prodzy.databinding.ActivityMainBinding
import com.crazycoder.prodzy.main.ui.favorites.FavoritesFragment
import com.crazycoder.prodzy.main.ui.productslist.views.ProductsListFragment

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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