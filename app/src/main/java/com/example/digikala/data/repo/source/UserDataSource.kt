package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.TokenResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {
    fun login(userName:String,password:String) : Single<TokenResponse>
    fun signUp(userName: String,password: String) : Single<MessageResponse>
    fun loadToken()
    fun saveToken(token:String , refreshToken:String)
    fun saveUsername(username: String)
    fun getUsername():String
    fun signOut()
}