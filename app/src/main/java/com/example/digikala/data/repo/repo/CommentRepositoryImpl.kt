package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.Comment
import com.example.digikala.data.repo.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl(val CommentRemote : CommentDataSource) : CommentRepository {
    override fun getAll(ProductId:Int): Single<List<Comment>> = CommentRemote.getAll(ProductId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}