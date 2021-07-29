package com.example.digikala.feature.main.main

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digikala.R
import com.example.digikala.common.animatorForviewems
import com.example.digikala.data.repo.Product
import com.example.digikala.feature.main.product.ProductDetailActivity
import com.example.digikala.services.http.loadingImage.LoadingImageService
import kotlinx.android.synthetic.main.item_product.view.*
import java.util.logging.Handler
import kotlin.coroutines.coroutineContext
const val ITEM_DEFAULT=0
const val ITEM_SMALL=1
const val ITEM_LARGE=2
class ProductListAdapter(var layoutState:Int= ITEM_DEFAULT, val loadingImageService: LoadingImageService) : RecyclerView.Adapter<ProductListAdapter.MyViewHolder>() {

    var productList = ArrayList<Product>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    var productClickListener : ProductClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutId=when(layoutState){
            ITEM_DEFAULT->R.layout.item_product
            ITEM_SMALL->R.layout.litem_product_list_small
            ITEM_LARGE->R.layout.item_product_list_large
            else->throw IllegalStateException("adapter must have item layout")
        }
        val view:View=LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return layoutState
    }

    override fun onBindViewHolder(holderMy: MyViewHolder, position: Int) {
        var uri:Uri =Uri.parse(productList[position].image)
        holderMy.itemView.IV_productItem.setImageURI(uri)
        holderMy.itemView.TV_productItem.text = productList[position].title
        holderMy.itemView.TV_currentPrice.text="${productList[position].price}تومان"

        if (productList[position].previousPrice ==null){
            holderMy.itemView.TV_previousPrice.text ="12560تومان"
        }else
        holderMy.itemView.TV_previousPrice.text = "${productList[position].previousPrice}تومان"

        holderMy.itemView.TV_previousPrice.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG

        if (productList[position].isFavorite){
            holderMy.itemView.IV_add_to_favorite.setImageResource(R.drawable.like_fill)
        }else  holderMy.itemView.IV_add_to_favorite.setImageResource(R.drawable.ic_favorites)



        holderMy.itemView.IV_add_to_favorite.setOnClickListener {
            productClickListener?.clickToAddFavorite(productList[position])
            productList[position].isFavorite = ! productList[position].isFavorite
            notifyItemChanged(position)
        }

        holderMy.itemView.setOnClickListener{
            animatorForviewems(it)
            android.os.Handler().postDelayed(Runnable {
             productClickListener?.onClickProduct(productList[position])
            },300)


        }
    }

    override fun getItemCount(): Int = productList.size

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    interface ProductClickListener{
        fun onClickProduct(product:Product)
        fun clickToAddFavorite(product: Product)
    }

}
