package com.example.digikala.services.http.loadingImage

import android.net.Uri
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.Exception

class FrescoLoadingImage : LoadingImageService {
    override fun loadingImage(imageView: SimpleDraweeView, imguri: String) {
        if (imageView is SimpleDraweeView){
            var uri =Uri.parse(imguri)
            imageView.setImageURI(uri)}
        else
          throw IllegalAccessException("doesn't load image")
    }
}