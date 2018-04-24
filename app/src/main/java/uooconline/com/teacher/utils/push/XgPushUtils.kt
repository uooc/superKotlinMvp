package uooconline.com.teacher.utils.push

import android.content.Context
import com.blankj.utilcode.util.RegexUtils
import com.mcxiaoke.packer.helper.PackerNg
import com.tencent.android.tpush.XGIOperateCallback
import com.tencent.android.tpush.XGPushConfig
import com.tencent.android.tpush.XGPushManager

/**
 * @author ricky.yao on 2017/8/10.
 */
object XgPushUtils {
    //初始配置
    fun init(applicationContext: Context, debug: Boolean) {
        val accessId: Long = -1
        val accessKey = ""
        XGPushConfig.enableDebug(applicationContext, debug)
        XGPushConfig.setAccessId(applicationContext, accessId)
        XGPushConfig.setInstallChannel(applicationContext, PackerNg.getChannel(applicationContext))
        XGPushConfig.setAccessKey(applicationContext, accessKey)
        XGPushConfig.setMiPushAppId(applicationContext, "")
        XGPushConfig.setMiPushAppKey(applicationContext, "")
        XGPushConfig.enableOtherPush(applicationContext, true)
    }

    fun registerPushBindAccount(applicationContext: Context, success: (token: String) -> Unit) {

        XGPushManager.registerPush(applicationContext, object : XGIOperateCallback {
            override fun onSuccess(data: Any?, flag: Int) {
                if (flag == XGPushManager.OPERATION_SUCCESS) {
                    val s = data as? String
                    if (RegexUtils.isMatch("[0-9A-Za-z]*", s ?: return))
                        success.invoke(s)
                }
            }

            override fun onFail(p0: Any?, p1: Int, p2: String?) {
                println()
            }
        })

    }
}