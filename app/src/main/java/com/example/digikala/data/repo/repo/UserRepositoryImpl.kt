package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.TokenContainer
import com.example.digikala.data.repo.TokenResponse
import com.example.digikala.data.repo.source.UserDataSource
import com.example.digikala.data.repo.source.UserLocalDataSource
import io.reactivex.Completable

class UserRepositoryImpl(val userRemoteDataSource:UserDataSource,
                         val userLocalDataSource: UserDataSource) : UserRepository {
    override fun login(userName: String, password: String): Completable {
       return userRemoteDataSource.login(userName,password).doOnSuccess {
           onSuccessLogin(userName,it)
       }.toCompletable()
    }

    override fun signUp(userName: String, password: String): Completable {
        return userRemoteDataSource.signUp(userName,password).flatMap {
            userRemoteDataSource.login(userName,password).doOnSuccess {itt->
                onSuccessLogin(userName,itt)
            }
        }.toCompletable()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }


    override fun getUsername(): String =userLocalDataSource.getUsername()

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.updateToken(null,null)
    }
    fun onSuccessLogin(username:String,tokenResponse: TokenResponse){
            TokenContainer.updateToken(tokenResponse.access_token.toString(),tokenResponse.refresh_token.toString())
        userLocalDataSource.saveToken(tokenResponse.access_token.toString(),tokenResponse.refresh_token.toString())
        userLocalDataSource.saveUsername(username)
    }
}