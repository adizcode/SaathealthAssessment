package com.github.adizcode.saathealthassessment

import android.app.Application
import com.github.adizcode.saathealthassessment.firebase.FirebaseService

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseService.initFirebaseAuth()
    }
}