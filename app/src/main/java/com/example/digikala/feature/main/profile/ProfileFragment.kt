package com.example.digikala.feature.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.digikala.R
import com.example.digikala.common.NikeFragment
import com.example.digikala.data.repo.TokenContainer
import com.example.digikala.feature.main.auth.AuthActivity
import com.example.digikala.feature.main.profile.favoriteproductList.FavoriteListActivity
import com.sevenlearn.nikestore.data.CartItemCount
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.greenrobot.eventbus.EventBus
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : NikeFragment() {
    val thisViewModel : ProfileViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TV_go_to_favoriteList.setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteListActivity::class.java))
        }
    }


    override fun onResume() {
        super.onResume()
        checkUserSignIn()

    }
    fun checkUserSignIn(){
        if (thisViewModel.isSignedIn()){
            TV_username_profile.text = thisViewModel.getUsername()
            TV_sign_in_out.text =resources.getString(R.string.signOut)
            TV_sign_in_out.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_out,0)
            TV_sign_in_out.setOnClickListener { thisViewModel.signOut()
            checkUserSignIn()
            }

        }else{
            TV_username_profile.text=resources.getString(R.string.GuestUser)
            TV_sign_in_out.text=resources.getString(R.string.textBTNLoginCreate)
            TV_sign_in_out.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_in,0)
            TV_sign_in_out.setOnClickListener {
                startActivity(Intent(requireContext(),AuthActivity::class.java))
            }
        }
    }

}