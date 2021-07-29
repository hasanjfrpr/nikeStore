package com.example.digikala.data.repo

import com.google.gson.annotations.SerializedName

data class SubmitOrderResult(

	@field:SerializedName("bank_gateway_url")
	val bankGatewayUrl: String? = null,

	@field:SerializedName("order_id")
	val orderId: Int? = null
)
