package com.me.multiplatform

import android.app.Application
import android.content.Context

class MeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Settings.filesDirPath = applicationContext.filesDir.path
        appContext = applicationContext
    }

    companion object{
        lateinit var appContext: Context
    }
}
