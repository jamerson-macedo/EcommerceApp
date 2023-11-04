package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.Product
import com.example.ecommerceapp.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(val firestore: FirebaseFirestore) : ViewModel() {
    private val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProducts: StateFlow<Resource<List<Product>>> = _specialProducts

    private val _bestDeals = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestdeals: StateFlow<Resource<List<Product>>> = _bestDeals

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProducts: StateFlow<Resource<List<Product>>> = _bestProducts
    init {
        fetchSpecialProducts()
        fetchBestProducts()
        fetchBestDeals()
    }

    fun fetchSpecialProducts() {
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }

        // paso o nome da coluna + o valor
        firestore.collection("Products")
            .whereEqualTo("category", "Special Products").get().addOnSuccessListener { result ->
                val specialProductObj =
                    result.toObjects(Product::class.java)// converte o resultado num produto
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(specialProductObj))
                }


            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }
    fun fetchBestProducts(){
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }

        // paso o nome da coluna + o valor
        firestore.collection("Products").get().addOnSuccessListener { result ->
                val specialProductObj =
                    result.toObjects(Product::class.java)// converte o resultado num produto
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Success(specialProductObj))
                }


            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Error(it.message.toString()))
                }
            }

    }
    fun fetchBestDeals(){
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }

        // paso o nome da coluna + o valor
        firestore.collection("Products")
            .whereEqualTo("category", "Special estilizado").get().addOnSuccessListener { result ->
                val specialProductObj =
                    result.toObjects(Product::class.java)// converte o resultado num produto
                viewModelScope.launch {
                    _bestDeals.emit(Resource.Success(specialProductObj))
                }


            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDeals.emit(Resource.Error(it.message.toString()))
                }
            }

    }


}