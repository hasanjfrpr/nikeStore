package com.example.digikala.feature.main.checkout

import androidx.lifecycle.MutableLiveData
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeView
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.Checkout
import com.example.digikala.data.repo.repo.OrderRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CheckoutViewModel(orderid:Int , orderRepository: OrderRepository): NikeViewModel() {
    val checkoutLiveData = MutableLiveData<Checkout>()
    init {
        orderRepository.checkout(orderid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable){
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value=t
                }

            })
    }
}