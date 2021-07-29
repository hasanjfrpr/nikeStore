package com.example.digikala.feature.main.shiping

import androidx.lifecycle.MutableLiveData
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeView
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.SubmitOrderResult
import com.example.digikala.data.repo.repo.OrderRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
const val PAYMENT_METHOD_COD="cash_on_delivery"
const val PAYMENT_METHOD_ONLINE="online"
class ShippingViewModel(val orderRepository:OrderRepository) : NikeViewModel() {


    fun submitOrder(firstName:String,lastName:String,postalCode:String
                    ,phoneNumber:String,address:String,paymentMethod:String):Single<SubmitOrderResult>{
       return orderRepository.submitOrder(firstName,lastName,postalCode,phoneNumber,address,paymentMethod)
    }

}