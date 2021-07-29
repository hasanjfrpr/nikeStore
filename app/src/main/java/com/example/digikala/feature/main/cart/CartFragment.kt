package com.example.digikala.feature.main.cart



import com.example.digikala.feature.main.cart.CartAdapter
import com.example.digikala.feature.main.cart.CartViewModel



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_DATA
import com.example.digikala.common.NikeCompletableObserver
import com.example.digikala.common.NikeFragment
import com.example.digikala.data.repo.EmptyState
import com.example.digikala.feature.main.auth.AuthActivity
import com.example.digikala.feature.main.product.ProductDetailActivity
import com.example.digikala.feature.main.shiping.ShippingActivity
import com.example.digikala.services.http.loadingImage.LoadingImageService
import com.sevenlearn.nikestore.data.CartItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.empty_state.*
import kotlinx.android.synthetic.main.empty_state.view.*
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CartFragment : NikeFragment(), CartAdapter.CartEvent {
    val thisViewModel: CartViewModel by viewModel()
    var cartAdapter:CartAdapter?=null
    val loadingImageService:LoadingImageService by inject()
    val compositeDisposable=CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thisViewModel.prograssBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }
        thisViewModel.cartItemLiveData.observe(viewLifecycleOwner){
          RV_cart.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            cartAdapter= CartAdapter(it as MutableList<CartItem>,loadingImageService,this)
            RV_cart.adapter=cartAdapter
        }
        thisViewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){
            cartAdapter?.let {adapter->
                adapter.purchaseDetail=it
                adapter.notifyItemChanged(adapter.cartItemList.size)
            }
        }

        thisViewModel.emptyStateLiveData.observe(viewLifecycleOwner){
            if (it.mustShow==true){
               val emptyView= showEmptyState(R.layout.empty_state)
                emptyView?.let { view->
                    view.TV_emptyMessage.text=getString( it.message)
                    view.mBTN_EmptyCallToAction.visibility=if (it.mustShowCallToAction==true) View.VISIBLE else View.GONE
                    view.mBTN_EmptyCallToAction.setOnClickListener {
                        startActivity(Intent(requireContext(),AuthActivity::class.java))
                    }
                    RV_cart.visibility=View.GONE
                    mBTN_pay.visibility=View.GONE
                    appBar_cart.visibility=View.GONE


                }

            }else  emptyStateRootView?.visibility=View.GONE
        }
        mBTN_pay.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(),ShippingActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA,thisViewModel.purchaseDetailLiveData.value)
            })
        }
    }

    override fun onStart() {
        thisViewModel.refreshCart()
        super.onStart()

    }

    override fun onRemoveCartItem(cartItem: CartItem) {
       thisViewModel.removeItemFromCart(cartItem)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(object : NikeCompletableObserver(compositeDisposable){
               override fun onComplete() {
                   cartAdapter?.deleteItemCart(cartItem)
               }

           })

    }

    override fun decreaseCartItem(cartItem: CartItem) {
        thisViewModel.decreaseItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter?.increaseAndDecrease(cartItem)
                }

            })
    }

    override fun increaseCartItem(cartItem: CartItem) {
        thisViewModel.increaseItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter?.increaseAndDecrease(cartItem)
                }

            })
    }

    override fun productImageClick(cartItem: CartItem) {
       startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
           putExtra(EXTRA_KEY_DATA,cartItem.product)
       })

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}


