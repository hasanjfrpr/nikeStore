package com.example.digikala.common

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent

fun dpToPx(dp: Int,context: Context): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun pxToDp(px: Int,context: Context): Int {
    return (px / context.resources.displayMetrics.density).toInt()
}
fun animatorForviewems(view:View){
    ObjectAnimator.ofFloat(view,"ScaleX",1f,0.86f).apply {
        duration=150
        start()
    }
    ObjectAnimator.ofFloat(view,"ScaleY",1f,0.86f).apply {
        duration=150
        start()
    }
    android.os.Handler().postDelayed(Runnable {
        ObjectAnimator.ofFloat(view,"ScaleX",0.86f,1f).apply {
            duration=150

            start()
        }
        ObjectAnimator.ofFloat(view,"ScaleY",0.86f,1f).apply {
            duration=150
            start()
        }
    },150)
}


 fun openUrlInCustomTab(context:Context,uri:String){
    try {
        val uri = Uri.parse(uri)
        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setStartAnimations(context,android.R.anim.fade_in,android.R.anim.fade_out)
        intentBuilder.setExitAnimations(context,android.R.anim.fade_in,android.R.anim.fade_out)
        val customTabsIntent =intentBuilder.build()
        customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        customTabsIntent.launchUrl(context,uri)
    }catch (e:Exception){}
 }