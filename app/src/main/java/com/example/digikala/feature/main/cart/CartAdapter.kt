package com.example.digikala.feature.main.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digikala.R
import com.example.digikala.services.http.loadingImage.LoadingImageService
import com.sevenlearn.nikestore.data.CartItem
import com.sevenlearn.nikestore.data.PurchaseDetail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_purchase_cart.view.*

const val RECYCLER_PURCHASE_DETAIL=1
const val RECYCLER_CART_ITEM_DETAIL=0

class CartAdapter(val cartItemList:MutableList<CartItem>,val loadingImageService: LoadingImageService,val cartEvent: CartEvent) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail:PurchaseDetail?=null


    inner class CartViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer{
        @SuppressLint("SetTextI18n")
        fun bind(cartItem:CartItem){
            containerView.TV_currentPrice_cart.text=cartItem.product.price.toString()
            containerView.TV_previousPrice_cart.text=cartItem.product.previousPrice.toString()+cartItem.product.discount
            containerView.TV_count_item.text=cartItem.count.toString()
            containerView.TV_product_title.text = cartItem.product.title
            loadingImageService.loadingImage(containerView.IV_product_image,cartItem.product.image.toString())
            containerView.BTN_deleteFromPurchaseBasket.setOnClickListener {
                cartEvent.onRemoveCartItem(cartItem)
            }
            if (cartItem.ProgressVisibilityCartItem==false){
                containerView.cart_progressBar.visibility=View.GONE
                containerView.TV_count_item.visibility=View.VISIBLE
            }else {containerView.cart_progressBar.visibility=View.VISIBLE
            containerView.TV_count_item.visibility=View.INVISIBLE}



            containerView.IV_increase.setOnClickListener{
                cartItem.ProgressVisibilityCartItem=true
                containerView.cart_progressBar.visibility=View.VISIBLE
                containerView.TV_count_item.visibility=View.INVISIBLE
                cartEvent.increaseCartItem(cartItem)
            }
            containerView.IV_decrease.setOnClickListener {
                if (cartItem.count>1){
                    cartItem.ProgressVisibilityCartItem=true
                    containerView.cart_progressBar.visibility=View.VISIBLE
                    containerView.TV_count_item.visibility=View.INVISIBLE
                    cartEvent.decreaseCartItem(cartItem)
                }

            }
            containerView.IV_product_image.setOnClickListener{
                cartEvent.productImageClick(cartItem)
            }

        }
    }
    class PurchasePriceViewHolder(override val containerView: View):RecyclerView.ViewHolder(containerView),LayoutContainer{
        fun bindPurchase(totalPrice:Int , shippingCost:Int,payAblePrice:Int){
            containerView.TV_shippingPrice.text=shippingCost.toString()
            containerView.TV_totalPurchase.text=totalPrice.toString()
            containerView.TV_payAblePrice.text=payAblePrice.toString()
        }

    }



    interface CartEvent{
        fun onRemoveCartItem(cartItem: CartItem)
        fun decreaseCartItem(cartItem: CartItem)
        fun increaseCartItem(cartItem: CartItem)
        fun productImageClick(cartItem: CartItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      if (viewType == RECYCLER_CART_ITEM_DETAIL)
          return CartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false))
        else return PurchasePriceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_cart,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartViewHolder){
            holder.bind(cartItemList[position])
        }else if(holder is PurchasePriceViewHolder){
            purchaseDetail?.let {
                holder.bindPurchase(it.total_price,it.shipping_cost,it.payable_price)
            }
        }
    }

    override fun getItemCount(): Int= cartItemList.size+1
    override fun getItemViewType(position: Int): Int {
        if (position == cartItemList.size) return RECYCLER_PURCHASE_DETAIL
        else return RECYCLER_CART_ITEM_DETAIL
    }

    fun deleteItemCart(cartItem: CartItem){
        val index=cartItemList.indexOf(cartItem)
        if(index > -1){
            cartItemList.removeAt(index)
            notifyItemRemoved(index)
        }
    }
    fun increaseAndDecrease(cartItem: CartItem) {
        val index = cartItemList.indexOf(cartItem)
        if (index > -1) {
            cartItemList[index].ProgressVisibilityCartItem = false
            notifyItemRemoved(index)
        }
    }
    
}
