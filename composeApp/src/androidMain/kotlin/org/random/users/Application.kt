package org.random.users

import KoinInitializer
import android.app.Application

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinInitializer(this).init()
    }
}
