package com.example.digikala.data.repo.source

import com.example.digikala.services.http.ApiService
import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.AddToCartResponse
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService):CartDataSource {

    override fun addToCArt(productID: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id",productID)
        }
    )

    override fun get(): Single<CartResponse> =apiService.getCart()

    override fun remove(cartItemId: Int): Single<MessageResponse> =apiService.removeItemFromCart(JsonObject().apply {
        addProperty("cart_item_id",cartItemId)
    })

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> = apiService.changeCountCart(JsonObject().apply {
        addProperty("cart_item_id",cartItemId)
        addProperty("count",count)
    })

    override fun getCartItemCount(): Single<CartItemCount> =apiService.getCartItemCount()

}