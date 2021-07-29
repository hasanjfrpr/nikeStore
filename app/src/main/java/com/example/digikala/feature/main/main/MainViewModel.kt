package com.example.digikala.feature.main.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.digikala.common.NikeCompletableObserver
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.*
import com.example.digikala.data.repo.repo.BannerRepository
import com.example.digikala.data.repo.repo.ProductRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

import io.reactivex.schedulers.Schedulers

class MainViewModel(private val productRepository: ProductRepository,bannerRepository: BannerRepository) : NikeViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()
    val productLiveDataMostSell = MutableLiveData<List<Product>>()
    val bannerLiveData = MutableLiveData<List<Banner>>()

    init {
        prograssBarLiveData.value = true
        productRepository.getProduct(PRODUCT_LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { prograssBarLiveData.value= false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                 productLiveData.value = t
                }

            })
        bannerRepository.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value =t
                }

            })

        productRepository.getProduct(PRODUCT_MOSTSELL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                   productLiveDataMostSell.value=t
                }

            })
    }
    fun addToFavorite(product:Product){
       if (product.isFavorite){
           productRepository.deleteFavoriteProduct(product)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                   override fun onComplete() {
                       Log.i("MainViewModel","add shod be list")
                   }

               })
       }else
           productRepository.addFavoriteProduct(product)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                   override fun onComplete() {
                       Log.i("MainViewModel","add shod be list")
                   }

               })

    }
}