package com.example.digikala.services.http.loadingImage

import com.facebook.drawee.view.SimpleDraweeView

interface LoadingImageService {
    fun loadingImage(imageView:SimpleDraweeView,imguri:String)
}