package uooconline.com.meizi

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import uooconline.com.meizi.action.IActionProcessor
import uooconline.com.meizi.action.MainActivityAction
import uooconline.com.nucleus.resource.Constant
import uooconline.com.nucleus.utils.router.RouterComponent
import java.util.concurrent.atomic.AtomicBoolean

class IComponentImpl : RouterComponent() {
    override fun onCalls(cc: CC): Boolean {
        if (initialized.compareAndSet(false, true)) {
            processors.forEach {
                actionProcessors[it.getActionName()] = it
            }
        }
        return actionProcessors[cc.actionName]?.onCall(cc) ?: run {
            CC.sendCCResult(cc.callId, CCResult.error("has not support for action:" + cc.actionName))
            false
        }
    }

    override fun getActivityMap(): Map<String, Class<*>> {
        return mapOf(
                Pair(Constant.Meizi.MainActivity, uooconline.com.meizi.MainActivity::class.java))
    }

    private val initialized = AtomicBoolean(false)
    private val actionProcessors = mutableMapOf<String, IActionProcessor>()
    private val processors = arrayListOf<IActionProcessor>(MainActivityAction())


    override fun getName(): String = Constant.Meizi.Meizi

}