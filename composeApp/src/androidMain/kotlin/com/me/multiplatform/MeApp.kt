package com.me.multiplatform

import android.app.Application

class MeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Settings.filesDirPath = applicationContext.filesDir.path
    }
}
