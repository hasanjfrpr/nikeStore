package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.TokenResponse
import com.example.digikala.services.http.ApiService
import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single
const val CLIENT_ID=2
const val CLIENT_SECRET="kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"
class UserRemoteDataSource(val apiService:ApiService) :UserDataSource {
    override fun login(userName: String, password: String): Single<TokenResponse> {
     return apiService.login(JsonObject().apply {
         addProperty("username",userName)
         addProperty("password",password)
         addProperty("grant_type", "password" )
         addProperty("client_secret", CLIENT_SECRET)
         addProperty("client_id", CLIENT_ID)
     })
    }

    override fun signUp(userName: String, password: String): Single<MessageResponse> {
     return apiService.signUp(JsonObject().apply {
         addProperty("email",userName)
         addProperty("password",password)
     })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun saveUsername(username: String) {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

}