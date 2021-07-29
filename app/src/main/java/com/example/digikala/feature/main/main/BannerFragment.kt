package com.example.digikala.feature.main.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_DATA
import com.example.digikala.data.repo.Banner
import com.example.digikala.services.http.loadingImage.LoadingImageService
import com.example.digikala.views.NikeLoadingImage
import kotlinx.android.synthetic.main.view_loading.*
import org.koin.android.ext.android.inject
import java.util.zip.Inflater

class BannerFragment : Fragment() {
    val imageViewLoading : LoadingImageService by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageView = inflater.inflate(R.layout.fragment_banner,container,false)
                as NikeLoadingImage
        val banner = requireArguments()
            .getParcelable<Banner>(EXTRA_KEY_DATA)?.image ?: throw IllegalAccessException("banner is empty")
        imageViewLoading.loadingImage(imageView,banner)
        return imageView
    }

    companion object{
        fun newInstance(banner:Banner) : BannerFragment{
            val bannerFragment = BannerFragment()
            val bundle =Bundle()
            bundle.putParcelable(EXTRA_KEY_DATA,banner)
            bannerFragment.arguments = bundle
            return bannerFragment
        }
    }
}