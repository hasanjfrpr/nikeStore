package com.example.digikala.feature.main.profile.favoriteproductList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digikala.R
import com.example.digikala.data.repo.Product
import com.example.digikala.services.http.loadingImage.LoadingImageService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_cart.view.TV_product_title
import kotlinx.android.synthetic.main.item_favorit_product.view.*

class FavoriteAdapter(val products:MutableList<Product>,val favoriteEvent: FavoriteEvent,val loadingImageService: LoadingImageService) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),LayoutContainer{
        fun bind(product:Product){
            containerView.TV_favorite_product_name.text=product.title
            loadingImageService.loadingImage(containerView.nikeLoadingImage,product.image.toString())
            containerView.setOnClickListener{
                favoriteEvent.onClick(product)
            }

            containerView.setOnLongClickListener {
                products.remove(product)
                favoriteEvent.onLongClick(product)
                notifyItemRemoved(adapterPosition)
                return@setOnLongClickListener false
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorit_product,parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(products[position])

    }

    override fun getItemCount(): Int =products.size


    interface FavoriteEvent{
        fun onClick(product: Product)
        fun onLongClick(product: Product)
    }
}