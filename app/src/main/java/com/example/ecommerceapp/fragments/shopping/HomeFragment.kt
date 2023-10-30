package com.example.ecommerceapp.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerceapp.R
import com.example.ecommerceapp.adapters.HomeViewPageAdapter
import com.example.ecommerceapp.databinding.FragmentHomeBinding
import com.example.ecommerceapp.fragments.categories.AccessoryFragment
import com.example.ecommerceapp.fragments.categories.ChairFragment
import com.example.ecommerceapp.fragments.categories.CupboardFragment
import com.example.ecommerceapp.fragments.categories.FurnitureFragment
import com.example.ecommerceapp.fragments.categories.MainCategoryFragment
import com.example.ecommerceapp.fragments.categories.TableFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )
        val pagerAdapter = HomeViewPageAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = pagerAdapter
        TabLayoutMediator(binding.tablayout, binding.viewpagerHome) { tab, pos ->
            when (pos) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Chair"
                2 -> tab.text = "Cupboard"
                3 -> tab.text = "Table"
                4 -> tab.text = "Acessory"
                5 -> tab.text = "Furniture"
            }

        }.attach()
    }
}