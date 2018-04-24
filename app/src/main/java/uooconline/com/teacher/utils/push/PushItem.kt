package uooconline.com.teacher.utils.push

import uooconline.com.nucleus.utils.impl.IUoocNoProguard


data class PushItem(val type: Int, val data: Data) : IUoocNoProguard {
    companion object {
        val TYPE_WEBVIEW = 1
        val TYPE_COURSE = 20        //课程公告
        val TYPE_ORG = 30           //机构公告
        val TYPE_PLATFORM = 40     //平台通知
        val TYPE_UUSER = 50         //个人消息
    }


    data class Data(
            val dialog_id: String, //14
            val to_uid: String, //375
            val url: String, //375
            val title: String,
            val announce_id: String
    ):IUoocNoProguard
}