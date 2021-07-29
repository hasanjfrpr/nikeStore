package com.example.digikala.data.repo

data class TokenResponse(
	val access_token: String? = null,
	val refresh_token: String? = null,
	val token_type: String? = null,
	val expires_in: Int? = null
)

