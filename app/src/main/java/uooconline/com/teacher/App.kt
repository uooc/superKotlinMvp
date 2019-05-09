package uooconline.com.teacher

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.tencent.bugly.beta.Beta
import uooconline.com.nucleus.utils.ext.getProcessName
import uooconline.com.nucleus.utils.impl.IApp
import uooconline.com.teacher.utils.sdk.HandleSDK
import java.lang.ref.WeakReference

open class App() :
        Application(), IApp {

    var mCurrentActivity: WeakReference<Activity>? = null

    override fun onCreate() {
        super.onCreate()
        //application 为 ProxyApplication
        when (getProcessName()) {
            packageName -> {
                commonInit(this, { mCurrentActivity = WeakReference(it) }, arrayListOf())
                //sdk和业务有关，只要配置了id和key，就可以拥有，热更新、推送这些，manifest文件注释去掉就阔以了，当然gradle.properties文件开启hotfix
                HandleSDK(this, isDevEnvironment())
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        if (BuildConfig.hotfix_enable) {
            Beta.installTinker(this)
        }
    }
}