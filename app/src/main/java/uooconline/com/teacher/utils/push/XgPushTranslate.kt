package uooconline.com.teacher.utils.push

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import uooconline.com.nucleus.utils.ext.applyStatusBarBlack


/**
 * XgPush过渡类
 */
class XgPushTranslate : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()
        obtianIntent(intent)
    }

    fun obtianIntent(intent: Intent?) {
    }



    override fun onDestroy() {
        super.onDestroy()
    }

}