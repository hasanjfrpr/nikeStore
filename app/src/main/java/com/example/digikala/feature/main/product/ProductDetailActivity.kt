package com.example.digikala.feature.main.product

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digikala.R
import com.example.digikala.common.*
import com.example.digikala.data.repo.Comment
import com.example.digikala.data.repo.Product
import com.example.digikala.feature.main.auth.AuthActivity
import com.example.digikala.feature.main.main.ProductListAdapter
import com.example.digikala.feature.main.profile.CommentListAdapter
import com.example.digikala.feature.main.profile.comment.AllCommentListActivity
import com.example.digikala.services.http.loadingImage.LoadingImageService
import com.google.android.material.appbar.AppBarLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailActivity : NikeActivity() , AddCommentDialog.DialogEvent {
    val loadingImageView: LoadingImageService by inject()
    val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val adapter = CommentListAdapter(false)
    var productName: String = ""
    val compositeDisposable=CompositeDisposable()
    var menu : Menu?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setupToolBar()
        var dialog = AddCommentDialog()
        dialog.dialogEvent=this
        add_comment.setOnClickListener {
            dialog.show(supportFragmentManager,null)
        }





        productDetailViewModel.prograssBarLiveData.observe(this) {
            setProgressIndicator(it)
        }
        productDetailViewModel.CommentLiveData.observe(this) {
            adapter.comment = it as ArrayList<Comment>
            if (it.size > 3) {
                MBTN_viewAllComments.visibility = View.VISIBLE
                MBTN_viewAllComments.setOnClickListener {
                    startActivity(Intent(this, AllCommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID, productDetailViewModel.ProductLiveData.value!!.id)
                    })
                }
            }
            RV_Comment.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            RV_Comment.adapter = adapter
        }

        productDetailViewModel.ProductLiveData.observe(this) {
            loadingImageView.loadingImage(IV_Product_detail, it.image.toString())
            TV_ProductName.text = it.title
            if (it.previousPrice == null) {
                TV_ProductOldPrice_Detail.text = "15265"
            } else
                TV_ProductOldPrice_Detail.text = it.previousPrice.toString()
            TV_ProductOldPrice_Detail.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            TV_ProductPrice_Detail.text = it.price.toString()
            productName = it.title.toString()
           if (it.isFavorite){
              menu?.getItem(R.id.detail_favorite)?.icon=ContextCompat.getDrawable(this,R.drawable.like_fill)
           }
        }
        appbar_Detail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBar, scrollCount
            ->
            val toolbarHeight=toolbar_detail.measuredHeight
            Log.i("dddd",scrollCount.toString())
            if (Math.abs(scrollCount) >= toolbarHeight) {
                toolbar_detail.subtitle = productName
            } else if (Math.abs(scrollCount) < toolbarHeight) {
                toolbar_detail.subtitle = ""
            }
        })


        BTN_addToCart.setOnClickListener {
            productDetailViewModel.onAddToCartBtn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:NikeCompletableObserver(compositeDisposable ){
                    override fun onComplete() {
                        showSnackBar(getString(R.string.successAddToCart))
                    }

                })
        }


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun setupToolBar() {
        toolbar_detail.title = ""
        toolbar_detail.setTitleTextColor(Color.BLACK)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun addComment(title: String, content: String) {
        productDetailViewModel.insetComment(title,content,productDetailViewModel.ProductLiveData.value!!.id!!)
    }
}