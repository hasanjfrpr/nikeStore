package com.example.digikala.feature.main.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.ProductListActivity
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_DATA
import com.example.digikala.common.NikeFragment
import com.example.digikala.common.dpToPx
import com.example.digikala.common.pxToDp
import com.example.digikala.data.repo.PRODUCT_LATEST
import com.example.digikala.data.repo.Product
import com.example.digikala.feature.main.product.ProductDetailActivity
import com.example.digikala.services.http.loadingImage.FrescoLoadingImage
import com.example.digikala.services.http.loadingImage.LoadingImageService
import com.example.digikala.views.NikeLoadingImage
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainFragment : NikeFragment(),ProductListAdapter.ProductClickListener {

    val mainViewModel : MainViewModel by viewModel()
    val productAdapter : ProductListAdapter by inject{ parametersOf(ITEM_DEFAULT)}
    val productAdapterMostSell : ProductListAdapter by inject{ parametersOf(ITEM_DEFAULT)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      productAdapter.productClickListener=this
        productAdapterMostSell.productClickListener=this


        mainViewModel.productLiveData.observe(this as LifecycleOwner){
            Log.i("MainFragment",it.toString())
            productAdapter.productList=it as ArrayList<Product>
            RV_latestProduct.adapter=productAdapter
            RV_latestProduct.layoutManager=LinearLayoutManager(requireContext()
                ,LinearLayoutManager.HORIZONTAL,false)


        }

        mainViewModel.productLiveDataMostSell.observe(this as LifecycleOwner){
            productAdapterMostSell.productList=it as ArrayList<Product>
            RV_mostSell.adapter=productAdapterMostSell
            RV_mostSell.layoutManager=LinearLayoutManager(requireContext()
                ,LinearLayoutManager.HORIZONTAL,false)

        }

        mainViewModel.prograssBarLiveData.observe(this as LifecycleOwner){
            setProgressIndicator(it)
        }
        mainViewModel.bannerLiveData.observe(this as LifecycleOwner){
            Log.i("MainFragment",it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this,it)
            bannerViewPagerMain.adapter=bannerSliderAdapter
            val height =((bannerViewPagerMain.measuredWidth- dpToPx(32,requireContext()) )*173)/328
            var layoutParams = bannerViewPagerMain.layoutParams
            layoutParams.height=height
            bannerViewPagerMain.layoutParams=layoutParams

            sliderIndicator.setViewPager2(bannerViewPagerMain)


        }

        MBTN_viewAllLatestProduct.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, PRODUCT_LATEST)
            })
        }
    }

    override fun onClickProduct(product: Product) {
        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })

    }

    override fun clickToAddFavorite(product: Product) {
        mainViewModel.addToFavorite(product)
    }
}