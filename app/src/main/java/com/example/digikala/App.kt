package com.example.digikala

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.digikala.data.repo.repo.*
import com.example.digikala.data.repo.room.AppDataBase
import com.example.digikala.data.repo.source.*
import com.example.digikala.feature.main.auth.AuthViewModel
import com.example.digikala.feature.main.cart.CartViewModel
import com.example.digikala.feature.main.checkout.CheckoutViewModel
import com.example.digikala.feature.main.list.ProductListViewModel
import com.example.digikala.feature.main.main.MainActivityViewModel
import com.example.digikala.feature.main.main.MainViewModel
import com.example.digikala.feature.main.main.ProductListAdapter
import com.example.digikala.feature.main.product.ProductDetailViewModel
import com.example.digikala.feature.main.profile.ProfileViewModel
import com.example.digikala.feature.main.profile.comment.AllCommentsListViewModel
import com.example.digikala.feature.main.profile.favoriteproductList.FavoriteViewModel
import com.example.digikala.feature.main.shiping.ShippingViewModel
import com.example.digikala.services.http.ApiService
import com.example.digikala.services.http.createApiServiceInstance
import com.example.digikala.services.http.loadingImage.FrescoLoadingImage
import com.example.digikala.services.http.loadingImage.LoadingImageService
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.startKoin
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)

        val myModule = module {
                single<ApiService> { createApiServiceInstance() }
            single<LoadingImageService> { FrescoLoadingImage() }

            single { Room.databaseBuilder(this@App,AppDataBase::class.java,"nikeDB").allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build() }

            factory<ProductRepository> {
                ProductRepositoryImp(ProductRemoteDataSource(get()),
                get<AppDataBase>().getDao()
            )
            }
            single { UserLocalDataSource(get()) }
            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }
            single<SharedPreferences>{this@App.getSharedPreferences("app_setting", MODE_PRIVATE)}
            single<UserRepository> {UserRepositoryImpl(UserRemoteDataSource(get()),UserLocalDataSource(get()))  }
            factory<BannerRepository> {BannerRepositoryImpl(BannerRemoteDataSource(get()))  }
            factory { (layoutState:Int)->ProductListAdapter(layoutState,get()) }

            factory<CommentRepository> {CommentRepositoryImpl(CommentRemoteDataSource(get()))  }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
            viewModel { MainViewModel(get(),get()) }
            viewModel { (bundle:Bundle)->ProductDetailViewModel(bundle,get(),get()) }
            viewModel { (productId:Int)->AllCommentsListViewModel(productId,get()) }
            viewModel { (sort:Int)->ProductListViewModel(get(),sort) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainActivityViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (order_id:Int)-> CheckoutViewModel(order_id,get())}
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteViewModel(get()) }
        }

        startKoin(androidContext = this@App , modules = listOf(myModule))
    }
}