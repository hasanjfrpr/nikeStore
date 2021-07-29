package com.example.digikala.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.digikala.R
import com.example.digikala.data.repo.EmptyState
import com.example.digikala.feature.main.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_loading.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NikeActivity:AppCompatActivity(),NikeView{
    override val root: CoordinatorLayout?
        get(){
            val viewGroup=window.decorView.findViewById(android.R.id.content) as ViewGroup
            if(viewGroup !is CoordinatorLayout){
                viewGroup.children.forEach {
                    if(it is CoordinatorLayout)
                        return it
                }
                throw IllegalAccessException("decorView must be instance of CoordinatorLayout")
            }else{
                return viewGroup
            }
        }
    override val contextView: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    EventBus.getDefault().register(this)
    }


    override fun onDestroy() {
      EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
abstract class NikeFragment : Fragment() ,NikeView{
    override val root: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val contextView: Context?
        get() = context

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}

interface NikeView {
    val root:CoordinatorLayout?
    val contextView : Context?

    fun setProgressIndicator(mustShow:Boolean){
        root?.let {
            contextView?.let {context->
                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow){
                    loadingView = LayoutInflater.from(context).inflate(R.layout.view_loading,
                    root,false)
                    it.addView(loadingView)

                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(nikeException: NikeException){
        contextView?.let {
            when(nikeException.type){
                NikeException.Type.SIMPLE->showSnackBar(nikeException.serverMessage ?: it.getString(nikeException.userFriendlyMessage))
                NikeException.Type.AUTH->{
                    it.startActivity(Intent(it,AuthActivity::class.java))
                    Toast.makeText(it,nikeException.serverMessage,Toast.LENGTH_SHORT).show()}
                NikeException.Type.DIALOG -> TODO()
            }
        }
    }

    fun showSnackBar(message:String,duration:Int=Snackbar.LENGTH_SHORT){
        root?.let {
            Snackbar.make(it,message,duration).show()
        }

    }
    fun showEmptyState(layoutID:Int):View?{
        root?.let {
            contextView?.let { context->
                var layoutEmptyState=it.findViewById<View>(R.id.emptyStateRootView)
                if (layoutEmptyState==null){
                    layoutEmptyState = LayoutInflater.from(context).inflate(layoutID,it,false)
                    it.addView(layoutEmptyState)
                }
                layoutEmptyState?.visibility=View.VISIBLE
                return layoutEmptyState
            }
        }
        return null
    }
}

abstract class NikeViewModel : ViewModel(){
    var prograssBarLiveData = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()
    val emptyStateLiveData=MutableLiveData<EmptyState>()
    override fun onCleared() {
       compositeDisposable.dispose()
        super.onCleared()
    }
}
