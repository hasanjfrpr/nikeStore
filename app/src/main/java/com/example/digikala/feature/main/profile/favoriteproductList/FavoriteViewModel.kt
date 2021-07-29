package com.example.digikala.feature.main.profile.favoriteproductList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.digikala.R


import com.example.digikala.common.NikeCompletableObserver
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.EmptyState
import com.example.digikala.data.repo.Product
import com.example.digikala.data.repo.repo.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(private val productRepository: ProductRepository):NikeViewModel() {

    val favoriteLiveData = MutableLiveData<List<Product>>()
    val emptyStateFavoriteLiveData  = MutableLiveData<EmptyState>()

    init {
        productRepository.getFavoriteProduct()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    if (t.isNullOrEmpty()){
                        emptyStateFavoriteLiveData.value = EmptyState(true, R.string.favoriteEmptyState)
                    }else
                    favoriteLiveData.value = t
                }
            })
    }

    fun removeFromFavorite(product:Product){
        productRepository.deleteFavoriteProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Log.i("FavoriteVewModel","item deleted")
                }

            })
    }
}