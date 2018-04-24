package uooconline.com.teacher.utils.sdk

import android.content.Context
import com.mcxiaoke.packer.helper.PackerNg
import com.tencent.bugly.Bugly
import com.umeng.analytics.MobclickAgent
import uooconline.com.nucleus.retrofit.ApiUtils
import uooconline.com.nucleus.utils.ext.toast
import uooconline.com.teacher.utils.push.XgPushUtils


class HandleSDK(applicationContext: Context, debug: Boolean) {

    init {
        val c = PackerNg.getChannel(applicationContext)
        val channel = when (debug) {
            true -> "dev"
            else -> when (c) {
                null, "" -> "Official"
                else -> c
            }
        }
        //bugly
        Bugly.init(applicationContext, "", false)
        Bugly.setAppChannel(applicationContext, channel)
        //友盟统计
        MobclickAgent.setDebugMode(debug)
        MobclickAgent.startWithConfigure(MobclickAgent.UMAnalyticsConfig(applicationContext, "", channel))
        //信鸽
        XgPushUtils.init(applicationContext, debug)
        XgPushUtils.registerPushBindAccount(applicationContext, {
            ApiUtils.xgTokenFlag = it
        })
    }
}

