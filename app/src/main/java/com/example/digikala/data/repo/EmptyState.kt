package com.example.digikala.data.repo

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow:Boolean,
    @StringRes var message:Int=0,
    val mustShowCallToAction:Boolean=false
    )