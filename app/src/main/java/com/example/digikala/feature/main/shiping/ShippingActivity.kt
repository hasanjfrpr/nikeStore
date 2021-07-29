package com.example.digikala.feature.main.shiping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_DATA
import com.example.digikala.common.EXTRA_KEY_ID
import com.example.digikala.common.NikeSingleObserver
import com.example.digikala.common.openUrlInCustomTab
import com.example.digikala.data.repo.SubmitOrderResult
import com.example.digikala.feature.main.cart.CartAdapter
import com.example.digikala.feature.main.checkout.CheckOutActivity
import com.sevenlearn.nikestore.data.PurchaseDetail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shiping.*
import org.koin.android.viewmodel.ext.android.viewModel

class ShippingActivity : AppCompatActivity(),View.OnClickListener {
    val thisViewModel:ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shiping)
        mBTN_cash_on_delivery.setOnClickListener(this)
        mBTN_online_payment.setOnClickListener(this)

        val purchaseDetail= intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
        purchaseDetail?.let {
            CartAdapter.PurchasePriceViewHolder(purchase_view_shipping).apply {
                bindPurchase(it.total_price,it.shipping_cost,it.payable_price)
            }
        }



    }

    override fun onClick(v: View?) {
        when(v?.id){
           R.id.mBTN_online_payment->{
                thisViewModel.submitOrder(TI_shipping_name.text.toString(),TI_shipping_LastName.text.toString()
                ,TI_shipping_postalCode.text.toString(),TI_shipping_phoneNumber.text.toString(),TI_shipping_address.text.toString(),
                PAYMENT_METHOD_ONLINE)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable){
                        override fun onSuccess(t: SubmitOrderResult) {
                            openUrlInCustomTab(this@ShippingActivity,t.bankGatewayUrl.toString())
                        }

                    })
               finish()
            }
            R.id.mBTN_cash_on_delivery->{
                thisViewModel.submitOrder(TI_shipping_name.text.toString(),TI_shipping_LastName.text.toString()
                    ,TI_shipping_postalCode.text.toString(),TI_shipping_phoneNumber.text.toString(),TI_shipping_address.text.toString(),
                    PAYMENT_METHOD_COD)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable){
                        override fun onSuccess(t: SubmitOrderResult) {
                            startActivity(Intent(this@ShippingActivity,CheckOutActivity::class.java).apply {
                                putExtra(EXTRA_KEY_ID,t.orderId)
                            })
                            finish()
                        }

                    })
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    compositeDisposable.clear()
    }
}