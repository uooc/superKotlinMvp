package uooconline.com.teacher.ui.activity.test

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import uooconline.com.teacher.R

class ShowActivityOnResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_activity_onresult)
        println()
        Handler().postDelayed({
            CC.sendCCResult(intent.getStringExtra("callId"), CCResult.success("fuck", "you"))
        },3000)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
