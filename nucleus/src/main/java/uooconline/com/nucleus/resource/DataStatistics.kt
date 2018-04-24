package uooconline.com.nucleus.resource

import android.content.Context
import android.text.TextUtils
import com.umeng.analytics.MobclickAgent


const val signin_signin = "signin_signin"//signin_signin	点击登录

object DataStatistics {
    var close = true
}

fun Context.MobEvent(eventId: String, vararg kVal: Pair<String, String>) {
    if (TextUtils.isEmpty(eventId) || DataStatistics.close)
        return
    if (kVal.isNotEmpty()) {
        val m = mutableMapOf<String, String>()
        kVal.forEach {
            m.put(it.first, it.second)
        }
        MobclickAgent.onEvent(this, eventId, m)
    } else {
        MobclickAgent.onEvent(this, eventId)
    }

}

