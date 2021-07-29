package com.example.digikala.data.repo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
	val linkType: Int? = null,
	val image: String? = null,
	val id: Int? = null,
	val linkValue: String? = null
):Parcelable

