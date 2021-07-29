package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Checkout
import com.example.digikala.data.repo.SubmitOrderResult
import io.reactivex.Single

interface OrderDataSource {

    fun submitOrder(firstName:String,lastName:String,postalCode:String
                    ,phoneNumber:String,address:String,paymentMethod:String) : Single<SubmitOrderResult>

    fun checkout(order_id:Int) : Single<Checkout>

}