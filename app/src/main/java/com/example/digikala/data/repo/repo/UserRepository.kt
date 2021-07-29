package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.TokenResponse
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun login(userName:String,password:String) : Completable
    fun signUp(userName: String,password: String) : Completable
    fun loadToken()
    fun getUsername():String
    fun signOut()
}