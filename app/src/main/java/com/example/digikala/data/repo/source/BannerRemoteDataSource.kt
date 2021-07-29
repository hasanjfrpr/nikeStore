package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Banner
import com.example.digikala.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService : ApiService) : BannerDataSource {
    override fun getBanner(): Single<List<Banner>> = apiService.getBanner()
}