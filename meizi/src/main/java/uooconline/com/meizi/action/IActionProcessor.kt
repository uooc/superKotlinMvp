package uooconline.com.meizi.action

import com.billy.cc.core.component.CC

interface IActionProcessor {
    fun getActionName(): String
    fun onCall(cc: CC): Boolean
}