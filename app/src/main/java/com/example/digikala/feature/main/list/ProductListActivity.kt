package com.e

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import com.example.digikala.feature.main.list.ProductListViewModel
import com.example.digikala.feature.main.main.ITEM_LARGE

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.digikala.R
import com.example.digikala.common.EXTRA_KEY_DATA
import com.example.digikala.common.NikeActivity
import com.example.digikala.data.repo.Product
import com.example.digikala.feature.main.main.ITEM_SMALL
import com.example.digikala.feature.main.main.ProductListAdapter
import com.example.digikala.feature.main.product.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity :NikeActivity(),ProductListAdapter.ProductClickListener  {
    val thisViewModel : ProductListViewModel by viewModel { parametersOf(intent.extras!!.getInt(
        EXTRA_KEY_DATA)) }
    val productListAdapter:ProductListAdapter by inject { parametersOf(ITEM_SMALL) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        productListAdapter.productClickListener=this

        var gridLayout=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        RV_ProductList.layoutManager=gridLayout
        RV_ProductList.adapter=productListAdapter
        thisViewModel.ProductList.observe(this){
            Log.i("ProductListActivity",it.toString())
            productListAdapter.productList=it as ArrayList<Product>
        }

        IV_grid.setOnClickListener {
            if (productListAdapter.layoutState == ITEM_SMALL){
                productListAdapter.layoutState= ITEM_LARGE
                gridLayout.spanCount=1
                IV_grid.setImageDrawable(resources.getDrawable(R.drawable.ic_square))
                productListAdapter.notifyDataSetChanged()
            }else{
                productListAdapter.layoutState= ITEM_SMALL
                gridLayout.spanCount=2
                IV_grid.setImageResource(R.drawable.ic_grid)
                productListAdapter.notifyDataSetChanged()

            }
        }
        thisViewModel.sortListLiveData.observe(this){
            TV_sortMode.text=resources.getString(it)
        }
        thisViewModel.prograssBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        V_selectSortMode.setOnClickListener {
            val builder=AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.sort))
            builder.setSingleChoiceItems(R.array.sortModeArrayList, thisViewModel.sortMode,
                object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        thisViewModel.changeSort(which)
                        dialog!!.dismiss()
                    }

                })
            val dialog=builder.create()
            dialog.show()
        }

        ic_back_commentList.setOnClickListener { finish() }

        }

    override fun onClickProduct(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun clickToAddFavorite(product: Product) {
     thisViewModel.addToFavorite(product)
    }
}