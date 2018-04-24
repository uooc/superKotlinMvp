package uooconline.com.nucleus.widget.richeditor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import uooconline.com.nucleus.widget.richeditor.core.RichEditor


//不可编辑的富文本显示，支持超链接拦截、图片大图查看
@SuppressLint("JavascriptInterface")
class RichView : RichEditor {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    init {
        setInputEnabled(false)
        overScrollMode = View.OVER_SCROLL_NEVER
        setOnClickInterceptListener(object : OnClickInterceptListener {
            override fun onHrefClick(url: String) {
                hrefAction?.invoke(url)
            }

            override fun onImgClick(urls: Array<out String>, index: Int, href: String?) {
                if (href == null) {
                    val intent = Intent()
                    intent.putExtra("imageUrls", urls)
                    intent.putExtra("curImageUrl", urls[index])
                    intent.setClass(context, PhotoBrowserActivity::class.java)
                    context.startActivity(intent)
                }
            }
        })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    private var hrefAction: ((url: String) -> Unit)? = null

    //拦截超链接点击
    fun preViewUri(hrefAction: ((url: String) -> Unit)) {
        this.hrefAction = hrefAction
    }

}