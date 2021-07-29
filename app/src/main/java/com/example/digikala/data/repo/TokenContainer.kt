package com.example.digikala.data.repo

import android.util.Log

object TokenContainer {
    var token: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun updateToken(token: String?, refreshToken: String?) {
        Log.i("TokenContainer",token?.substring(0,10)+refreshToken?.subSequence(0,10))
        this.token = token
        this.refreshToken = refreshToken
    }
}