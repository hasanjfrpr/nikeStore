package com.example.digikala.data.repo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Entity(tableName = "favorite")
@Parcelize
data class Product(
	val image: String? = null,
	val price: Int? = null,
	var discount: Int? = null,
	@PrimaryKey(autoGenerate = false)
	val id: Int? = null,
	val title: String? = null,
	val previousPrice: Int? = null,
	val status: Int? = null
):Parcelable{
	var isFavorite:Boolean = false
}
const val PRODUCT_LATEST=0
const val PRODUCT_MOSTSELL=1
const val PRODUCT_PRICE_DESC=2
const val PRODUCT_PRICE_ASC=3


