package com.example.digikala.services.http

import androidx.lifecycle.MutableLiveData
import com.example.digikala.data.repo.*
import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.AddToCartResponse
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProduct(@Query("sort") sort: String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanner(): Single<List<Banner>>

    @GET("comment/list")
    fun getComment(@Query("product_id") ProductId: Int): Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject): Single<AddToCartResponse>

    @POST("cart/remove")
    fun removeItemFromCart(@Body jsonObject: JsonObject) : Single<MessageResponse>

    @GET("cart/list")
    fun getCart() : Single<CartResponse>

    @POST("cart/changeCount")
    fun changeCountCart(@Body jsonObject: JsonObject) :Single<AddToCartResponse>

    @GET("cart/count")
    fun getCartItemCount() : Single<CartItemCount>


    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject): Single<TokenResponse>

    @POST("user/register")
    fun signUp(@Body jsonObject: JsonObject): Single<MessageResponse>

    @POST("auth/token")
    fun refresh(@Body jsonObject: JsonObject) : Call<TokenResponse>

    @POST("order/submit")
    fun orderSubmit(@Body jsonObject: JsonObject) : Single<SubmitOrderResult>

    @GET("order/checkout")
    fun checkout(@Query("order_id") order_id:Int) : Single<Checkout>
}

fun createApiServiceInstance(): ApiService {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{
            val oldRequest=it.request()
            val newRequestBuilder =oldRequest.newBuilder()
            if (TokenContainer.token != null)
                newRequestBuilder.addHeader("Authorization","Bearer " +
                        "${TokenContainer.token}")

            newRequestBuilder.addHeader("Content-Type","application/json")
            newRequestBuilder.method(oldRequest.method,oldRequest.body)
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
    return retrofit.create(ApiService::class.java)
}