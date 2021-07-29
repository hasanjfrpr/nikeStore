package com.example.digikala.data.repo

import com.google.gson.annotations.SerializedName

data class Checkout(

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null,

	@field:SerializedName("purchase_success")
	val purchaseSuccess: Boolean? = null,

	@field:SerializedName("payable_price")
	val payablePrice: Int? = null
)
