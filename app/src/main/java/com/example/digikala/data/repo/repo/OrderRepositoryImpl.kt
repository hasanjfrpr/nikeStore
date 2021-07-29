package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.Checkout
import com.example.digikala.data.repo.SubmitOrderResult
import com.example.digikala.data.repo.source.OrderDataSource
import io.reactivex.Single

class OrderRepositoryImpl(val orderRemoteDataSource:OrderDataSource) : OrderRepository {
    override fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderRemoteDataSource.submitOrder(firstName,lastName,postalCode,phoneNumber,address
        ,paymentMethod)
    }

    override fun checkout(order_id: Int): Single<Checkout> {
        return orderRemoteDataSource.checkout(order_id)
    }
}