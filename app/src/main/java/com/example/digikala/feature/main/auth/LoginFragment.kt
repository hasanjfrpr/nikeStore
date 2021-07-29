package com.example.digikala.feature.main.auth

import android.os.Bundle
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.digikala.R
import com.example.digikala.common.NikeCompletableObserver
import com.example.digikala.common.NikeFragment
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment :NikeFragment() {
    val thisViewModel:AuthViewModel by viewModel()
    val compositeDisposable =CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    mBTN_login.setOnClickListener {
        thisViewModel.login(ET_username.text.toString(),ET_password.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                   requireActivity().finish()

                }

            })
    }
        mBTN_enter_to_signUp.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout_auth,SignUpFragment())
            }.commit()
        }
    }



    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()

    }


}