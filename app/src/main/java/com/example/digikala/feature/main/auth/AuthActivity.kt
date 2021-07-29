package com.example.digikala.feature.main.auth

import android.os.Bundle
import com.example.digikala.R
import com.example.digikala.common.NikeActivity

class AuthActivity : NikeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    }

    override fun onStart() {
        super.onStart()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout_auth, LoginFragment())

        }.commit()
    }
   
}


