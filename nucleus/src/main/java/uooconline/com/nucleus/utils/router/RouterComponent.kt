/*
 * Copyright (C) 2017 Ricky.yao https://github.com/vihuela
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */

package uooconline.com.nucleus.utils.router

import android.content.Intent
import android.os.Bundle
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent

abstract class RouterComponent : IComponent {

    override fun onCall(cc: CC): Boolean {
        if (cc.getParamItem<Boolean>("startActivity") == true) {
            routerActivity(cc)
            return false
        }
        if (cc.getParamItem<Boolean>("startActivityForResult") == true) {
            routerActivity(cc)
            return true
        }
        return onCalls(cc)
    }

    abstract fun onCalls(cc: CC): Boolean

    abstract fun getActivityMap(): Map<String, Class<*>>

    private fun routerActivity(cc: CC) {
        val param = Bundle()

        cc.params.forEach { t, u ->
            param.putString(t, u.toString())
        }

        cc.context.startActivity(Intent(cc.context, getActivityMap()[cc.actionName]).apply {
            putExtra("callId", cc.callId)
            putExtras(param)
        })
    }
}