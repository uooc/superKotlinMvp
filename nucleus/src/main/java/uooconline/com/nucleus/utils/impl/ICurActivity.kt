package uooconline.com.nucleus.utils.impl

import android.app.Activity
import android.app.Application
import android.os.Bundle

interface ICurActivity {


    fun observeCurActivity(app: Application, curActivity: (current: Activity) -> Unit) {

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {


            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                curActivity.invoke(activity)
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }
}