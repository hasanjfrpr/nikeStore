package com.example.digikala.data.repo.source

import com.example.digikala.data.repo.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanner() : Single<List<Banner>>
}