package com.example.digikala.services.http

import com.example.digikala.data.repo.TokenContainer
import com.example.digikala.data.repo.TokenResponse
import com.example.digikala.data.repo.source.CLIENT_ID
import com.example.digikala.data.repo.source.CLIENT_SECRET
import com.example.digikala.data.repo.source.UserLocalDataSource
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback

class NikeAuthenticator : Authenticator,KoinComponent {
    val apiService:ApiService by inject()
    val userLocalDataSource : UserLocalDataSource by inject()
    override fun authenticate(route: Route?, response: Response): Request? {
        if (TokenContainer.token!=null && TokenContainer.refreshToken!=null
            && !response.request.url.pathSegments.last().equals("token",false))

        try {
                var token=refresh()
            if (token.isEmpty())
                return null

            return response.request.newBuilder().addHeader("Authorization","Bearer $token").build()
        }catch (e:Exception){

        }
        return null
    }


    fun refresh():String{
        var accessToken:String?=null
        apiService.refresh(JsonObject().apply {
            addProperty("grant_type","refresh_token")
            addProperty("refresh_token",TokenContainer.refreshToken)
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        }).enqueue(object: Callback<TokenResponse>{
            override fun onResponse(
                call: Call<TokenResponse>,
                response: retrofit2.Response<TokenResponse>
            ) {
                if (response.isSuccessful){
                    TokenContainer.updateToken(response.body()!!.access_token,
                        response.body()!!.refresh_token)
                    userLocalDataSource.saveToken(response.body()!!.access_token.toString(),
                        response.body()!!.refresh_token.toString())
                    accessToken=response.body()!!.access_token
                }
            }


            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                accessToken=""
            }

        })

        return accessToken.toString()
    }
}