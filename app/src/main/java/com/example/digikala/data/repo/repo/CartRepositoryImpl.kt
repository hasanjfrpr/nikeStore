package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.source.CartDataSource
import com.sevenlearn.nikestore.data.AddToCartResponse
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single

class CartRepositoryImpl(val cartRemoteDataSource: CartDataSource): CartRepository {
    override fun addToCArt(productID: Int): Single<AddToCartResponse> = cartRemoteDataSource.addToCArt(productID)
    override fun get(): Single<CartResponse> = cartRemoteDataSource.get()

    override fun remove(cartItemId: Int): Single<MessageResponse> =cartRemoteDataSource.remove(cartItemId)

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =cartRemoteDataSource.changeCount(cartItemId,count)

    override fun getCountItemCount(): Single<CartItemCount> =cartRemoteDataSource.getCartItemCount()
}