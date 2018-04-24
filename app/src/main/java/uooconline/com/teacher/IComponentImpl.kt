package uooconline.com.teacher

import com.billy.cc.core.component.CC
import uooconline.com.nucleus.resource.Constant
import uooconline.com.nucleus.utils.router.RouterComponent
import uooconline.com.teacher.ui.activity.test.ShowActivity
import uooconline.com.teacher.ui.activity.test.ShowActivityOnResult

class IComponentImpl : RouterComponent() {
    override fun onCalls(cc: CC): Boolean {
        println()
        return false
    }

    override fun getActivityMap(): Map<String, Class<*>> =
            mapOf(
                    Pair(Constant.App.ShowActivity, ShowActivity::class.java),
                    Pair(Constant.App.ShowActivityOnResult, ShowActivityOnResult::class.java))


    override fun getName(): String = Constant.App.App

}