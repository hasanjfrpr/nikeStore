package com.example.digikala.common

import android.util.Log
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

abstract class NikeSingleObserver<T>(val compositeDisposable: CompositeDisposable) : SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        Log.i("tagError", "error")
        EventBus.getDefault().post(NikeExceptionMapper.map(e))
    }
}