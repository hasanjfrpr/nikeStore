package com.example.digikala.feature.main.profile.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_ID
import com.example.digikala.common.NikeActivity
import com.example.digikala.data.repo.Comment
import com.example.digikala.feature.main.profile.CommentListAdapter
import kotlinx.android.synthetic.main.activity_all_comment_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AllCommentListActivity :NikeActivity() {
    val thisViewModel:AllCommentsListViewModel by viewModel { parametersOf(intent.extras!!.getInt(
        EXTRA_KEY_ID))  }
    val commentListAdapter = CommentListAdapter(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_comment_list)
        ic_back_commentList.setOnClickListener{
            finish()
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
}