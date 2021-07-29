package com.example.digikala.feature.main.profile.favoriteproductList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_DATA
import com.example.digikala.common.EXTRA_KEY_ID
import com.example.digikala.common.NikeActivity
import com.example.digikala.data.repo.EmptyState
import com.example.digikala.data.repo.Product
import com.example.digikala.feature.main.product.ProductDetailActivity
import com.example.digikala.services.http.loadingImage.LoadingImageService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorit_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteListActivity : NikeActivity(),FavoriteAdapter.FavoriteEvent {
    val thisViewModel : FavoriteViewModel by viewModel()
    val loadingImageService : LoadingImageService by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorit_list)

        thisViewModel.favoriteLiveData.observe(this){


                RV_FavoriteList.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                RV_FavoriteList.adapter =
                    FavoriteAdapter(it as MutableList<Product>, this, loadingImageService)

        }

        thisViewModel.emptyStateFavoriteLiveData.observe(this) {
            if (it.mustShow) {
                showEmptyState(R.layout.empty_state_favorite)
            }
        }

        IV_favorite_info.setOnClickListener {
            showSnackBar(getString(R.string.showInfoFavorite),Snackbar.LENGTH_LONG)
        }
        IV_favorite_back.setOnClickListener { finish() }

    }

    override fun onClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onLongClick(product: Product) {
        thisViewModel.removeFromFavorite(product)
    }
}