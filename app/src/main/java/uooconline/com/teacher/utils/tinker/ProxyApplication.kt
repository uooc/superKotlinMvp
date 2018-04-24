package uooconline.com.teacher.utils.tinker

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

open class ProxyApplication : TinkerApplication(ShareConstants.TINKER_ENABLE_ALL, "uooconline.com.teacher.App",
        "com.tencent.tinker.loader.TinkerLoader", false) {
    override fun onCreate() {
        super.onCreate()
    }
}