package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.Banner
import com.example.digikala.data.repo.source.BannerDataSource
import com.example.digikala.data.repo.source.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImpl(val BannerRemote : BannerDataSource): BannerRepository {
    override fun getBanner(): Single<List<Banner>> =BannerRemote.getBanner()
}