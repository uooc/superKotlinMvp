package uooconline.com.meizi.action

import android.content.Intent
import com.billy.cc.core.component.CC
import uooconline.com.meizi.MainActivity
import uooconline.com.nucleus.resource.Constant

class MainActivityAction : IActionProcessor {
    override fun getActionName(): String = Constant.Meizi.MainActivity

    override fun onCall(cc: CC): Boolean {
        cc.context.startActivity(Intent(cc.context, MainActivity::class.java).apply {
            putExtra("callId", cc.callId)
        })
        return true//不立即调用CC.sendCCResult,返回true
    }
}