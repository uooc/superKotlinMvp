package uooconline.com.teacher.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.blankj.utilcode.util.TimeUtils
import com.just.agentweb.AgentWeb
import com.just.agentweb.PermissionInterceptor
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet
import kotlinx.android.synthetic.main.activity_common_web.*
import uooconline.com.nucleus.resource.REFRESH_WEBVIEW
import uooconline.com.nucleus.retrofit.ApiPath
import uooconline.com.nucleus.retrofit.ApiUtils
import uooconline.com.nucleus.ui.base.BaseActivity
import uooconline.com.nucleus.utils.eventbus.Event
import uooconline.com.nucleus.utils.ext.applyStatusBarBlack
import uooconline.com.nucleus.utils.ext.applyStatusMargin
import uooconline.com.nucleus.utils.ext.toast
import uooconline.com.teacher.R
import uooconline.com.teacher.databinding.ActivityCommonWebBinding
import uooconline.com.teacher.presenter.CommonWebViewPresenter
import uooconline.com.teacher.ui.view.ICommonWebView
import uooconline.com.teacher.utils.JsInterface
import java.util.*


//通用的webView，必须有两个参数：title 、 url、handleBack（可选）、useShare（可选）
//router(RouterImpl.CommonWebActivity,Pair("title","用户协议"),Pair("url","http://www.baidu.com"),Pair("handleBack","true"),Pair("useShare","false"))
open class CommonWebActivity : BaseActivity<CommonWebViewPresenter, ActivityCommonWebBinding>(), ICommonWebView {

    public var mAgentWeb: AgentWeb? = null
    public var mPreAgentWeb: AgentWeb.PreAgentWeb? = null
    var mCommonAgentBuilder: AgentWeb.CommonAgentBuilder? = null
    var title: String? = null//标题
    var url: String = ""//url
    var useUrlTitle: Boolean = false//是否使用url获取标题
    var handleBack: Boolean = true//是否处理返回事件
    var useShare: Boolean = false//是否拥有分享
    private var isSuccess = false
    private var isError = false
    var mEventId: String? = ""//事件id，用于友盟埋点
    var mStayTime: Date? = null//页面停留时长
    var isClearAllHistory = false //是否清除所有历史痕迹

    override fun setMessage(error: Any, content: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setData(beanList: List<*>, loadMore: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun start(context: Context, title: String, url: String, handleBack: String, useShare: String = "false") {
            val intent = Intent(context, CommonWebActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            intent.putExtra("handleBack", handleBack)
            intent.putExtra("useShare", useShare)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()
        applyStatusMargin(mContain)
        showContent()
        //友盟
        mEventId = intent.getStringExtra("eventId")
        if (!TextUtils.isEmpty(mEventId))
            mStayTime = TimeUtils.getNowDate()
        //url
        val oUrl = intent.getStringExtra("url")
        url = if (oUrl != null && (oUrl.startsWith("http"))) oUrl else "${ApiPath.getApiPath()}/$oUrl"
        if(url.contains("@*@")){
            url = url.replace("@*@","#")
        }
        //title
        title = intent.getStringExtra("title")

        useUrlTitle = (TextUtils.isEmpty(title))
        useShare = intent.getStringExtra("useShare")?.toBoolean() ?: false
        handleBack = intent.getStringExtra("handleBack")?.toBoolean() ?: true

        with(mTitlebar ?: return) {
            setTitle(this@CommonWebActivity.title)
            addLeftImageButton(R.mipmap.ic_nav_back, -1)?.setOnClickListener {
                finish()
            }
            //分享
            if (useShare || url.contains("course")) {
                addRightImageButton(R.mipmap.icon_topbar_overflow_blue, -1).setOnClickListener {
                    showShareBottomDialog(this@CommonWebActivity.title!!, "kotlinDemo", url, BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                }
            }
        }

        mCommonAgentBuilder = AgentWeb.with(this)
                .setAgentWebParent(mWebLayout, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator().setIndicatorColor(resources.getColor(R.color.colorPrimary))
        mPreAgentWeb = mCommonAgentBuilder!!
                .setPermissionInterceptor(mPermissionInterceptor)
                .setReceivedTitleCallback({ _, title ->
                    if (useUrlTitle == true) {
                        this.title = title
                        mTitlebar?.setTitle(title)
                    }
                })
                .setWebViewClient(webClient)
                .setWebChromeClient(mWebChromeClient)
                .additionalHttpHeader("xgTokenFlag", ApiUtils.xgTokenFlag)
                .additionalHttpHeader("versionFlag", "${ApiUtils.versionFlag}")
                .additionalHttpHeader("machineFlag", ApiUtils.machineFlag)
                .additionalHttpHeader("sourceFlag", ApiUtils.sourceFlag)
                .additionalHttpHeader("Authorization", "Bearer ${ApiUtils.userFlag}")
                .createAgentWeb().ready()
        mAgentWeb = mPreAgentWeb?.go(url)
        mAgentWeb?.webCreator?.get()?.overScrollMode = View.OVER_SCROLL_NEVER
        mAgentWeb?.clearWebCache()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        //Android 端 ， AndroidInterface 是一个注入类 ，里面有一个无参数方法：callAndroid
        mAgentWeb!!.jsInterfaceHolder.addJavaObject("android", JsInterface(mAgentWeb!!, this))
    }

    /************************        JS交互逻辑           ****************************************/
    /**
     * 显示toast 消息
     */
    fun showJsMessage(str: String) {
        toast(str)
    }
    /************************        页面网络状态监听及各种状态  处理           ****************************************/

    override fun onStateViewRetryListener() {
        super.onStateViewRetryListener()
        mCommonAgentBuilder?.additionalHttpHeader("Authorization", "Bearer ${ApiUtils.userFlag}")
                ?.additionalHttpHeader("xgTokenFlag", ApiUtils.xgTokenFlag)
                ?.additionalHttpHeader("versionFlag", "${ApiUtils.versionFlag}")
                ?.additionalHttpHeader("machineFlag", ApiUtils.machineFlag)
                ?.additionalHttpHeader("sourceFlag", ApiUtils.sourceFlag)
        mAgentWeb?.webCreator?.get()?.reload()
        isSuccess = false
        isError = false
    }

    //WebChromeClient
    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            //do you work
//            Log.i(CommonWebActivity::class.toString(), "url:" + newProgress)
        }
    }

    private var webClient: WebViewClient = object : WebViewClient() {
        //处理网页加载失败时
        override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
        }


        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }
    }

    private var mPermissionInterceptor: PermissionInterceptor = PermissionInterceptor { url, permissions, action ->
        //AgentWeb 在触发某些敏感的 Action 时候会回调该方法， 比如定位触发 。
        //例如 https//:www.baidu.com 该 Url 需要定位权限， 返回false ，如果版本大于等于23 ， agentWeb 会动态申请权限 ，true 该Url对应页面请求定位失败。
        //该方法是每次都会优先触发的 ， 开发者可以做一些敏感权限拦截 。
//        Log.i(CourseDetailWebActivity::class.toString(), "url:$url  permission:$permissions action:$action")
        false
    }

    override fun getLayoutId(): Int = R.layout.activity_common_web

    override fun finish() {
        if (handleBack && mAgentWeb!!.back()) return
        else super.finish()
    }

    override fun onPause() {
        super.onPause()
        mAgentWeb?.webLifeCycle?.onPause()
    }

    override fun onResume() {
        super.onResume()
        mAgentWeb?.webLifeCycle?.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb?.webLifeCycle?.onDestroy()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun isRegisterEventBus(): Boolean = true
    override fun <T> onEvent(event: Event<T>?) {
        super.onEvent(event)
        when (event?.code) {
            REFRESH_WEBVIEW -> {
                onStateViewRetryListener()
            }
        }
    }


    private fun showShareBottomDialog(title: String, subTitle: String, targetUrl: String, imageUrl: Bitmap) {
        val builder = QMUIBottomSheet.BottomGridSheetBuilder(this)
        builder.addItem(R.mipmap.bubble_wechat, "微信", 0, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.bubble_moment,"朋友圈", 1, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.bubble_qq, "QQ", 2, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener { dialog, itemView ->
                    dialog.dismiss()
                    when (itemView.tag as Int) {
                        0 -> {
                            //微信分享
                        }
                        1 -> {
                            //朋友圈分享
                        }
                        2 -> {
                            //qq分享
                        }
                    }
                }.build().show()
    }
}