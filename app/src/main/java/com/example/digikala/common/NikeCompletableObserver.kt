package com.example.digikala.common

import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

abstract class NikeCompletableObserver( val compositeDisposable: CompositeDisposable) : CompletableObserver{
    override fun onError(e: Throwable) {
        EventBus.getDefault().post(NikeExceptionMapper.map(e))
    }

    override fun onSubscribe(d: Disposable) {
       compositeDisposable.add(d)
    }
}