package com.example.digikala.feature.main.profile

import androidx.lifecycle.MutableLiveData
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.TokenContainer
import com.example.digikala.data.repo.repo.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) :NikeViewModel() {

    fun getUsername():String = userRepository.getUsername()
    fun isSignedIn():Boolean = TokenContainer.token !=null
    fun signOut()=userRepository.signOut()
}