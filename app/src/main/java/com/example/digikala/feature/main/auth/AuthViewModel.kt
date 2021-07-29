package com.example.digikala.feature.main.auth

import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.repo.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository:UserRepository) : NikeViewModel() {

    fun login(username:String , password:String) : Completable{
        prograssBarLiveData.value=true
        return userRepository.login(username,password).doFinally{
            prograssBarLiveData.postValue(false)
        }
    }

    fun signUp(username:String,password: String):Completable{
        prograssBarLiveData.value=true
        return userRepository.signUp(username,password).doFinally{
            prograssBarLiveData.postValue(false)
        }
    }

}