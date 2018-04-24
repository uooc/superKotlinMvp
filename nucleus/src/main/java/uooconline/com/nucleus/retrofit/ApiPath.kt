package uooconline.com.nucleus.retrofit

import android.support.annotation.StringDef
import uooconline.com.nucleus.utils.ext.pref

object ApiPath {
    const val Internal = "Internal"
    const val Test = "Test"
    const val Beta = "Beta"
    const val Formal = "Formal"
    val desMap = mapOf(
            Pair(Internal, "内网环境"),
            Pair(Test, "测试环境"),
            Pair(Beta, "Beta环境"),
            Pair(Formal, "正式环境"))

    @StringDef(Internal, Test, Beta, Formal)
    annotation class EnvironmentType

    var currentEnvir by pref(Test)


    fun setEnvironment(@EnvironmentType environment: String) {
        currentEnvir = environment
    }

    fun getApiPath(envir: String = currentEnvir): String =
            when (envir) {
                Internal -> "http://192.168.1.210"
                Test -> "http://test.uooconline.com"
                Beta -> "http://beta.uooconline.com"
                Formal -> "http://www.uooconline.com"
                else -> "http://www.uooconline.com"
            }

    fun getDocPath(): String =
            when (currentEnvir) {
                Internal -> "http://192.168.1.210:8087"
                Test -> "http://doc-test.uooconline.com"
                Beta -> "http://doc-beta.uooconline.com"
                Formal -> "http://doc.uooconline.com"
                else -> "http://doc.uooconline.com"
            }

}