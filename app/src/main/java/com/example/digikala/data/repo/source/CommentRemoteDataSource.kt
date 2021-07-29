package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Comment
import com.example.digikala.services.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {
    override fun getAll(ProductId:Int): Single<List<Comment>> =apiService.getComment(ProductId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}