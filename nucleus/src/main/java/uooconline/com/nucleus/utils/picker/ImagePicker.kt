package uooconline.com.nucleus.utils.picker

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import uooconline.com.nucleus.R
import java.io.File


//在fragment或activity中覆写onActivityResult：
/**
 * ①ImagePicker.init(view()) 仅一次
 * ②imagePicker.set(this, mBinding.top, mRxPermissions)
 * ③onActivityResult中：imagePicker.onActivityResult(this, requestCode, resultCode, data ?: return, {})
 */
class ImagePicker {

    var AUTHORITY = "provider"//需要与manifest一致
    val REQUEST_CODE = 0xff1

    companion object {
        //通常不必初始化，但是多进程使用此框架时，需要在入口处初始化
        fun <Context> init(context: Context) {
            var AUTHORITY = "provider"
            val matisse: Matisse = when (context) {
                is Fragment -> {
                    AUTHORITY = "${context.activity?.packageName}.$AUTHORITY"
                    Matisse.from(context)
                }
                is Activity -> {
                    AUTHORITY = "${context.packageName}.$AUTHORITY"
                    Matisse.from(context)
                }
                else -> return
            }
            matisse
                    .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP), false)
                    .theme(R.style.Matisse_Dracula)
                    .capture(true)
                    .captureStrategy(CaptureStrategy(true, AUTHORITY))
        }
    }

    fun <Context> set(context: Context, targetView: View, mRxPermissions: RxPermissions, startNow: Boolean, maX_SEL: Int = 1) {
        val matisse: Matisse = when (context) {
            is Fragment -> {
                AUTHORITY = "${context.activity?.packageName}.$AUTHORITY"
                Matisse.from(context)
            }
            is Activity -> {
                AUTHORITY = "${context.packageName}.$AUTHORITY"
                Matisse.from(context)
            }
            else -> throw IllegalArgumentException("context must be Fragment Or Activity")
        }

        RxView.clicks(targetView)
                .compose(mRxPermissions.ensure<Any>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe {
                    if (it) {
                        matisse
                                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP), false)
                                .theme(R.style.Matisse_Dracula)
                                .countable(maX_SEL != 1)
                                .capture(true)
                                .showSingleMediaType(true)
                                .captureStrategy(CaptureStrategy(true, AUTHORITY))
                                .maxSelectable(maX_SEL)
                                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(targetView.context.resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                .thumbnailScale(0.85f)
                                .imageEngine(GlideEngine())
                                .forResult(REQUEST_CODE)
                        return@subscribe
                    }
                    Toast.makeText(targetView.context, targetView.context.getString(R.string.nucleus_check_permission), Toast.LENGTH_SHORT).show()
                }
        if (startNow)
            targetView.performClick()
    }

    fun onActivityResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?, getFiles: (files: ArrayList<File>) -> Unit) {

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val listFile = arrayListOf<File>()
            val obtainResult = Matisse.obtainPathResult(data ?: return)
            obtainResult.forEach {
                getTargetFile(context, it, {
                    listFile.apply {
                        add(it ?: return@apply)
                    }.let {
                        //所有选择的图片都已压缩完毕
                        if (listFile.size == obtainResult.size) {
                            getFiles.invoke(listFile)
                        }
                    }
                })
            }
        }
    }

    fun getTargetFile(context: Context, path: String, getFile: (f: File?) -> Unit) {
        //异步压缩
        Luban.with(context)
                .load(File(path))
                .setCompressListener(object : OnCompressListener {
                    override fun onSuccess(f: File?) {
                        getFile.invoke(f)
                    }

                    override fun onError(e: Throwable?) {
                        getFile.invoke(null)
                    }

                    override fun onStart() {
                    }
                }).launch()
    }
}