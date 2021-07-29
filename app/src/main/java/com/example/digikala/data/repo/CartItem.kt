package com.sevenlearn.nikestore.data

import com.example.digikala.data.repo.Product

data class CartItem(
    val cart_item_id: Int,
    var count: Int,
    val product: Product,
    var ProgressVisibilityCartItem:Boolean = false
)