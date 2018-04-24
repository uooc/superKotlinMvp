package uooconline.com.teacher.utils.push

import android.content.Context
import com.tencent.android.tpush.*

class XgPushReceiver : XGPushBaseReceiver() {
    override fun onSetTagResult(p0: Context?, p1: Int, p2: String?) {
    }

    override fun onNotifactionShowedResult(p0: Context?, p1: XGPushShowedResult?) {
    }

    override fun onUnregisterResult(p0: Context?, p1: Int) {
    }

    override fun onDeleteTagResult(p0: Context?, p1: Int, p2: String?) {
    }

    override fun onRegisterResult(p0: Context?, p1: Int, p2: XGPushRegisterResult?) {
    }

    override fun onTextMessage(p0: Context?, p1: XGPushTextMessage?) {
    }

    override fun onNotifactionClickedResult(p0: Context?, p1: XGPushClickedResult?) {
    }


}