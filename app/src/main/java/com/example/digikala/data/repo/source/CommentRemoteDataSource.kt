package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Comment
import com.example.digikala.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {
    override fun getAll(ProductId:Int): Single<List<Comment>> =apiService.getComment(ProductId)
    override fun insert(title: String, content: String, ProductId: Int): Single<Comment> = apiService.insert(JsonObject().apply {
        addProperty("title",title)
        addProperty("content",content)
        addProperty("product_id",ProductId)
    })


}