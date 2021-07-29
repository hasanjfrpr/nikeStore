package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {
    fun getProduct(sort:Int) : Single<List<Product>>

    fun getFavoriteProduct() : Single<List<Product>>
    fun addFavoriteProduct(product: Product) : Completable
    fun deleteFavoriteProduct(product: Product): Completable
}