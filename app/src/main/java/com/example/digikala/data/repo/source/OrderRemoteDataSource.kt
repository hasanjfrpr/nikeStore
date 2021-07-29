package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Checkout
import com.example.digikala.data.repo.SubmitOrderResult
import com.example.digikala.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(val apiService: ApiService):OrderDataSource {
    override fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> = apiService.orderSubmit(JsonObject().apply {
        addProperty("first_name",firstName)
        addProperty("last_name",lastName)
        addProperty("postal_code",postalCode)
        addProperty("mobile",phoneNumber)
        addProperty("address",address)
        addProperty("payment_method",paymentMethod)
    })

    override fun checkout(order_id: Int): Single<Checkout> =apiService.checkout(order_id)
}