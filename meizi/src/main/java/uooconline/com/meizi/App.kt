package uooconline.com.meizi

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import uooconline.com.nucleus.utils.ext.getProcessName
import uooconline.com.nucleus.utils.impl.IApp

class App : Application(), IApp {
    override fun onCreate() {
        super.onCreate()
        when (getProcessName()) {
            packageName -> {
                commonInit(this, { }, arrayListOf())
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}