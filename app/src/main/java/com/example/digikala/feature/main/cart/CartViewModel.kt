package com.example.digikala.feature.main.cart

import android.util.EventLog
import androidx.lifecycle.MutableLiveData
import com.example.digikala.R
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.NikeViewModel
import com.example.digikala.data.repo.EmptyState
import com.example.digikala.data.repo.TokenContainer
import com.example.digikala.data.repo.repo.CartRepository
import com.sevenlearn.nikestore.data.CartItem
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import com.sevenlearn.nikestore.data.PurchaseDetail
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class CartViewModel(val cartRepository:CartRepository) : NikeViewModel(){
    val cartItemLiveData=MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData=MutableLiveData<PurchaseDetail>()

    private fun getCartItems(){
        if (!TokenContainer.token.isNullOrEmpty()){
            prograssBarLiveData.value=true
            emptyStateLiveData.value= EmptyState(false)
            cartRepository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { prograssBarLiveData.value=false }
                .subscribe (object : NikeSingleObserver<CartResponse>(compositeDisposable){
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()){
                            cartItemLiveData.value=t.cart_items
                            purchaseDetailLiveData.value= PurchaseDetail(t.payable_price,
                            t.shipping_cost,t.total_price)
                        }else emptyStateLiveData.value= EmptyState(true, R.string.cartEmptyState)
                    }

                })
        }else
        emptyStateLiveData.value=EmptyState(true,R.string.EmptyStateMessage,true)
    }
    fun removeItemFromCart(cartItem: CartItem):Completable{
        return cartRepository.remove(cartItem.cart_item_id).doAfterSuccess{refreshPurchaseDetail()
        cartItemLiveData.value?.let {
            if (it.size == 0){
                emptyStateLiveData.value = EmptyState(true,R.string.cartEmptyState)
            }
        }
            var counter = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            counter.let {
                it.count -=cartItem.count
                EventBus.getDefault().postSticky(it)
            }

        }.toCompletable()
    }
    fun increaseItemCount(cartItem: CartItem):Completable=cartRepository.changeCount(cartItem.cart_item_id,
    ++cartItem.count).doAfterSuccess{refreshPurchaseDetail()
    var counter = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
        counter.let {
            it.count +=1
            EventBus.getDefault().postSticky(it)
        }


    }.toCompletable()

    fun decreaseItemCount(cartItem: CartItem):Completable=cartRepository.changeCount(cartItem.cart_item_id,
    --cartItem.count).doAfterSuccess {
        refreshPurchaseDetail()
        var counter = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
        counter.let {
            it.count-=1
            EventBus.getDefault().postSticky(it)
        }
    }.toCompletable()

        fun refreshCart(){
            getCartItems()
        }

        fun refreshPurchaseDetail(){
            cartItemLiveData.value?.let {cartItemList->
                purchaseDetailLiveData.value?.let {purchaseDetail->
                    var totalPrice=0
                    var payAble=0
                    cartItemList.forEach{
                        totalPrice +=(it.product.price!! )*(it.count)
                        payAble += (it.product.price!!-it.product.discount!!)*it.count
                    }

                    purchaseDetail.payable_price =payAble
                    purchaseDetail.total_price = totalPrice
                    purchaseDetailLiveData.postValue(purchaseDetail)


                }
            }
        }
    }


