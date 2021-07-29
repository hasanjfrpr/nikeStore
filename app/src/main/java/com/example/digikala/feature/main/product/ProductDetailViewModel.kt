package com.example.digikala.feature.main.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.digikala.common.EXTRA_KEY_DATA
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.Comment
import com.example.digikala.data.repo.Product
import com.example.digikala.data.repo.repo.CartRepository
import com.example.digikala.data.repo.repo.CommentRepository
import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(bundle:Bundle,private val commentRepository: CommentRepository
,val cartRepository:CartRepository):NikeViewModel() {
    val ProductLiveData =MutableLiveData<Product>()
    val CommentLiveData = MutableLiveData<List<Comment>>()

    init {
        ProductLiveData.value=bundle.getParcelable(EXTRA_KEY_DATA)
        prograssBarLiveData.value=true
        commentRepository.getAll(ProductLiveData.value!!.id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { prograssBarLiveData.value=false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    Log.i("ProductDetailViewModel",t.toString())
                    CommentLiveData.value=t
                }

            })
    }
    fun onAddToCartBtn():Completable {
        Log.i("producDetailViewModel",ProductLiveData.value!!.id!!.toString())
        return cartRepository
            .addToCArt(ProductLiveData.value!!.id!!.toInt()).toCompletable()
    }

    @SuppressLint("CheckResult")
    fun insetComment(title:String, content:String, productId:Int) :Completable{
       return commentRepository.insert(title,content,productId).toCompletable()
    }

}