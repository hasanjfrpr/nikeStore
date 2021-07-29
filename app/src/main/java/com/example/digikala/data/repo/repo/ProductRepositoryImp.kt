package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.Product
import com.example.digikala.data.repo.source.ProductLocalDataSource
import com.example.digikala.data.repo.source.ProductRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImp(val remoteDataSource: ProductRemoteDataSource,
                           val localDataSource: ProductLocalDataSource) : ProductRepository {
    override fun getProduct(sort:Int): Single<List<Product>> = localDataSource.getFavoriteProduct().flatMap { favorites->
        remoteDataSource.getProduct(sort).doOnSuccess {
            val favoritesID = favorites.map {
                it.id
            }
            it.forEach{product->
                if (favoritesID.contains(product.id))
                product.isFavorite=true
            }
        }
    }

    override fun getFavoriteProduct(): Single<List<Product>> = localDataSource.getFavoriteProduct()

    override fun addFavoriteProduct(product: Product): Completable = localDataSource.addFavoriteProduct(product)

    override fun deleteFavoriteProduct(product: Product): Completable {
        product.isFavorite=false
        return localDataSource.deleteFavoriteProduct(product)
    }
}