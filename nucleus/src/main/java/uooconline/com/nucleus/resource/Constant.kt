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

package uooconline.com.nucleus.resource

const val NET_STATE = "netAvailable"

const val XgTokenFlag = "xgTokenFlag"
const val VersionFlag = "versionFlag"
const val MachineFlag = "machineFlag"
const val ProductFlag = "productFlag"
const val SourceFlag = "sourceFlag"
const val UserFlag = "userFlag"


const val ACCOUNT_CONFILICT = 0xff1
const val CERTIFI_UN_AUTH = 0xff2
const val ACCOUNT_UNLOGIN = 0xff3
const val CERTIFI_AUTHING = 0xff4
const val REFRESH_WEBVIEW = 0xff5
class Constant {

    object App {
        const val App = "app"
        const val MainActivity = "${App}MainActivity"
        const val ShowActivity = "${App}ShowActivity"
        const val ShowActivityOnResult = "${App}ShowActivityOnResult"

    }

    object Meizi {
        const val Meizi = "meizi"
        const val MainActivity = "${Meizi}MainActivity"
    }

    object News {
        const val News = "news"
        const val MainActivity = "${News}MainActivity"
    }


}