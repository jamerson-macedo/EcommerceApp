package com.example.ecommerceapp.data

import android.health.connect.datatypes.units.Percentage

data class Product(
    val id: String,//
    val name: String? = "",//
    val description: String? = "",//
    val category: String? = "",//
    val price: Float?,//
    val offerPercentage: Float?=null,//
    val images:List<String>,//
    val colors:List<Int>? = null,//
    val sizes:List<String>? = null,//

) {
    constructor():this("0","","","",0f, images = emptyList())
}