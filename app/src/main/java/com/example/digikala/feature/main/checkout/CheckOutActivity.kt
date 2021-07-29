package com.example.digikala.feature.main.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_ID
import kotlinx.android.synthetic.main.activity_check_out.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : AppCompatActivity() {
    val thisViewModel : CheckoutViewModel by viewModel {
        val uri: Uri?= intent.data
        if (uri!=null) parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        else parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        thisViewModel.checkoutLiveData.observe(this){
            paymentPrice.text = it.payablePrice.toString()
           paymentStatus.text = it.paymentStatus.toString()
            checkout_status_order_title.text=if (it.purchaseSuccess!!) "خرید با موفقیت انجام شد" else "تراکنش نا موفق"
        }
    }
}