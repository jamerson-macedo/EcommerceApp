package com.example.ecommerceapp.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.adapters.BestDealsAdapter
import com.example.ecommerceapp.adapters.BestProductAdapter
import com.example.ecommerceapp.adapters.SpecialProductsAdapter
import com.example.ecommerceapp.databinding.FragmentMainCategoryBinding
import com.example.ecommerceapp.util.Resource
import com.example.ecommerceapp.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

private val TAG = "MainCategoryFragment "
@AndroidEntryPoint
class MainCategoryFragment : Fragment(R.layout.fragment_main_category) {
    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
    private lateinit var bestDealsAdapter: BestDealsAdapter
    private lateinit var bestProductAdapter: BestProductAdapter
    private val viewmodel by viewModels<MainCategoryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpecialRv()
        setupBestDeals()
        setupBestProducts()
        lifecycleScope.launchWhenStarted {
            viewmodel.specialProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        specialProductsAdapter.differ.submitList(it.data)
                        hideloading()
                    }

                    is Resource.Error -> {
                        hideloading()
                        Log.i(TAG, it.message.toString())
                        Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
                    }

                    else -> {Unit}
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewmodel.bestProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        bestProductAdapter.differ.submitList(it.data)
                        hideloading()
                    }

                    is Resource.Error -> {
                        hideloading()
                        Log.i(TAG, it.message.toString())
                        Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
                    }

                    else -> {Unit}
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewmodel.bestdeals.collectLatest {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        bestDealsAdapter.differ.submitList(it.data)
                        hideloading()
                    }

                    is Resource.Error -> {
                        hideloading()
                        Log.i(TAG, it.message.toString())
                        Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
                    }

                    else -> {Unit}
                }
            }
        }

    }

    private fun setupBestProducts() {
        bestProductAdapter= BestProductAdapter()
        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2,GridLayoutManager.VERTICAL,false)
            adapter = bestProductAdapter
        }
    }

    private fun setupBestDeals() {
        bestDealsAdapter= BestDealsAdapter()
        binding.rvBestDeals.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = bestDealsAdapter
        }
    }

    private fun hideloading() {
        binding.progressBest.visibility=View.GONE
    }

    private fun showLoading() {
        binding.progressBest.visibility=View.VISIBLE
    }

    private fun setupSpecialRv() {
        specialProductsAdapter = SpecialProductsAdapter()
        binding.rvSpecialProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = specialProductsAdapter
        }
    }
}