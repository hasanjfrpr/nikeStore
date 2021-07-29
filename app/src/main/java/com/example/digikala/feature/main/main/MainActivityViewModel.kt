package com.example.digikala.feature.main.main

import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.TokenContainer
import com.example.digikala.data.repo.repo.CartRepository
import com.sevenlearn.nikestore.data.CartItem
import com.sevenlearn.nikestore.data.CartItemCount
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainActivityViewModel(private val repository: CartRepository):NikeViewModel() {

    fun getCountItemCart(){
       if (!TokenContainer.token.isNullOrEmpty()){
           repository.getCountItemCount()
               .subscribeOn(Schedulers.io())

               .subscribe(object : NikeSingleObserver<CartItemCount>(compositeDisposable){
                   override fun onSuccess(t: CartItemCount) {
                       EventBus.getDefault().postSticky(t)
                   }
               })
       }
    }

}
