package com.example.digikala.data.repo.source

import androidx.room.*
import com.example.digikala.data.repo.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource : ProductDataSource {
    override fun getProduct(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM favorite")
    override fun getFavoriteProduct(): Single<List<Product>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addFavoriteProduct(product: Product): Completable

    @Delete
    override fun deleteFavoriteProduct(product: Product): Completable
}