package com.example.digikala.data.repo.repo

import com.example.digikala.data.repo.Banner
import io.reactivex.Single

interface BannerRepository {

    fun getBanner() : Single<List<Banner>>
}