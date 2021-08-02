package com.example.digikala.feature.main.profile.comment

import androidx.lifecycle.MutableLiveData
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.Comment
import com.example.digikala.data.repo.repo.CommentRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AllCommentsListViewModel(val productId:Int,val commentRepo:CommentRepository) : NikeViewModel() {
    val CommentListLiveData =MutableLiveData<List<Comment>>()

    init {
      getAllComment()
    }
    fun getAllComment(){
        prograssBarLiveData.value=true
        commentRepo.getAll(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { prograssBarLiveData.value=false }
            .subscribe(object :NikeSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    CommentListLiveData.value=t
                }

            })
    }
    fun addComment(title:String , content:String ):Completable{
        return commentRepo.insert(title,content,productId).toCompletable()
    }
}