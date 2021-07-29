package com.example.digikala.feature.main.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.digikala.R
import com.example.digikala.common.NikeCompletableObserver
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.Product
import com.example.digikala.data.repo.repo.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(val Repository:ProductRepository,var sortMode:Int) : NikeViewModel() {
    val ProductList =MutableLiveData<List<Product>>()
    var sortListLiveData = MutableLiveData<Int>()
    var sortModeTitleList= arrayOf(R.string.sortLatest,R.string.sortPopular,R.string.sortHighPrice
    ,R.string.sortLowPrice)
    init {
      getProducts()
        sortListLiveData.value=sortModeTitleList[sortMode]
    }
    fun getProducts(){
        prograssBarLiveData.value=true
        Repository.getProduct(sortMode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { prograssBarLiveData.value=false }
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    ProductList.value=t
                }

            })
    }
    fun changeSort(sort:Int){
        this.sortMode=sort
        sortListLiveData.value=sortModeTitleList[sortMode]
        getProducts()
    }

    fun addToFavorite(product:Product){
        Repository.addFavoriteProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Log.i("MainViewModel","add shod be list")
                }

            })
    }

}