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

import android.content.Context
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult

//startActivity
fun Context.routerActivity(moduleName: String, actionName: String, vararg pairs: Pair<String, *>) {
    CC
            .obtainBuilder(moduleName)
            .setActionName(actionName)
            .setParams(mapOf(*pairs))
            .addParam("startActivity", true)
            .addParam("startActivityForResult", false)
            .build()
            .call()
}

//startActivityForResult
//在目标页面使用：CC.sendCCResult(intent.getStringExtra("callId"), CCResult.success("fuck", "you"))
fun Context.routerActivityForResult(moduleName: String, actionName: String, results: (results: Map<String, Any>) -> Unit, vararg pairs: Pair<String, *>) {
    CC
            .obtainBuilder(moduleName)
            .setActionName(actionName)
            .setParams(mapOf(*pairs))
            .addParam("startActivity", false)
            .addParam("startActivityForResult", true)
            .build()
            .callAsyncCallbackOnMainThread { _: CC, result: CCResult ->
                if (result.code == CCResult.CODE_SUCCESS) {
                    results.invoke(result.dataMap)
                }
            }
}

//manual
fun Context.router(moduleName: String, actionName: String, vararg pairs: Pair<String, *>): CC =
        CC
                .obtainBuilder(moduleName)
                .setActionName(actionName)
                .setParams(mapOf(*pairs))
                .build()