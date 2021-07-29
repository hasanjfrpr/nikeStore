package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Product
import com.example.digikala.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {
    override fun getProduct(sort:Int): Single<List<Product>> = apiService.getProduct(sort.toString())

    override fun getFavoriteProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addFavoriteProduct(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFavoriteProduct(product: Product): Completable {
        TODO("Not yet implemented")
    }
}