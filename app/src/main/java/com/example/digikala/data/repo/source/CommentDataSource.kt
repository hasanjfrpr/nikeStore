package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Comment
import io.reactivex.Single

interface CommentDataSource {
    fun getAll(ProductId:Int) : Single<List<Comment>>
    fun insert() : Single<Comment>
}