package uooconline.com.teacher.utils

import android.content.Context
import android.text.TextUtils
import android.webkit.JavascriptInterface
import com.just.agentweb.AgentWeb
import org.json.JSONArray
import uooconline.com.teacher.ui.activity.CommonWebActivity


/**
 * Created by leiyaogen on 17/11/27.
 *
 * Js 交互工具类:Js调用app
 */
class JsInterface(private val agent: AgentWeb, val context: Context) {

    /**
     * 消息Toast提示
     */
    @JavascriptInterface
    fun alertmsg(str: String) {
        if (TextUtils.isEmpty(str))
            return
        val jsObject = JSONArray(str ?: "")
        var obj = jsObject[0] //  提示的toast
        if (obj != null && !TextUtils.isEmpty(obj as String)) {
            (context as CommonWebActivity).showJsMessage(obj)
        }
    }

    // －－－－－－－－－－－－－－－ 楚河   js逻辑处理     汉界－－－－－－－－－－－－－－－－－－－－－
}