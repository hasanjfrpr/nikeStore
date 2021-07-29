package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.Comment
import io.reactivex.Single

interface CommentRepository {
    fun getAll(ProductId:Int) : Single<List<Comment>>
    fun insert(title:String , content:String , productId:Int) : Single<Comment>
}