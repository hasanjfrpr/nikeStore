package com.example.digikala.feature.main.profile.comment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_ID
import com.example.digikala.common.NikeActivity
import com.example.digikala.common.NikeCompletableObserver
import com.example.digikala.data.repo.Comment
import com.example.digikala.data.repo.TokenContainer
import com.example.digikala.feature.main.auth.AuthActivity
import com.example.digikala.feature.main.product.AddCommentDialog
import com.example.digikala.feature.main.profile.CommentListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_all_comment_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AllCommentListActivity :NikeActivity(), AddCommentDialog.DialogEvent{
    val thisViewModel:AllCommentsListViewModel by viewModel { parametersOf(intent.extras!!.getInt(
        EXTRA_KEY_ID))  }
    val commentListAdapter = CommentListAdapter(true)
    val compositeDisposable=CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_comment_list)

        ic_back_commentList.setOnClickListener{
            finish()
        }

        val dialog =AddCommentDialog()
        dialog.dialogEvent=this
        fab_add_comment.setOnClickListener{
            if (TokenContainer.token.isNullOrEmpty()){
                startActivity(Intent(this,AuthActivity::class.java))
            }
            dialog.show(supportFragmentManager,null)
               
        }


        thisViewModel.prograssBarLiveData.observe(this){
            setProgressIndicator(it)
        }
        thisViewModel.CommentListLiveData.observe(this){
            RV_commentList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        commentListAdapter.comment=it as ArrayList<Comment>
            RV_commentList.adapter=commentListAdapter
        }
    }

    override fun addComment(title: String, content: String) {
        thisViewModel.addComment(title,content).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    thisViewModel.getAllComment()
                    commentListAdapter.notifyDataSetChanged()
                }

            })

    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}