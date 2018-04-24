/*
 * Copyright (C) 2017 Ricky.yao https://github.com/vihuela
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */

package uooconline.com.nucleus.utils.ext

import android.animation.Animator
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.text.*
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.Utils
import com.github.refresh.RefreshCustomerLayout
import com.github.refresh.interfaces.IRefreshStateView
import com.hitomi.glideloader.GlideImageLoader
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.jakewharton.rxbinding2.widget.RxTextView
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import uooconline.com.nucleus.R
import java.io.File
import java.util.regex.Pattern
import kotlin.reflect.KClass

//RefreshCustomerLayout
fun RefreshCustomerLayout.setViewStateListener(showEmpty: () -> Unit,
                                               showContent: () -> Unit,
                                               showLoading: () -> Unit,
                                               showMessage: (message: String) -> Unit,
                                               showMessageFromNet: (error: Any, content: String) -> Unit): RefreshCustomerLayout =
        this.setViewStateListener(object : IRefreshStateView {
            override fun showMessageFromNet(error: Any, content: String) {
                showMessageFromNet.invoke(error, content)
            }

            override fun showLoading() {
                showLoading.invoke()
            }

            override fun showContent() {
                showContent.invoke()
            }

            override fun showEmpty() {
                showEmpty.invoke()
            }

            override fun showMessage(content: String) {
                showMessage.invoke(content)
            }
        })


fun RefreshCustomerLayout.setRefreshListener(onRefresh: (rcl: RefreshCustomerLayout) -> Unit,
                                             onLoadMore: (rcl: RefreshCustomerLayout, targetPage: Int) -> Unit): RefreshCustomerLayout =
        setRefreshListener(object : RefreshCustomerLayout.IRefreshListener {
            override fun onLoadMore(rcl: RefreshCustomerLayout, targetPage: Int) {
                onLoadMore.invoke(rcl, targetPage)
            }

            override fun onRefresh(rcl: RefreshCustomerLayout) {
                onRefresh.invoke(rcl)
            }
        })

//View的左右抖动动画
fun View.shake() {
    startAnimation(AnimationUtils.loadAnimation(context, R.anim.nucleus_shake))
}

//view的透明动画
fun View.toggleFade(exec: (visibility: Int) -> Unit = {}) {
    when (visibility) {
        View.VISIBLE -> {
            animate().alpha(0f).setDuration(300).setInterpolator(AccelerateDecelerateInterpolator()).setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    visibility = View.GONE
                    exec.invoke(visibility)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            }).start()
        }
        View.INVISIBLE, View.GONE -> {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f).setDuration(300).setInterpolator(AccelerateDecelerateInterpolator()).setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    exec.invoke(visibility)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            }).start()
        }
    }
}

//textView文本高亮
fun TextView.addHighLight(highLightText: String, highLightColor: Int) {
    val text: String = text?.toString() ?: return
    val targetSpan: ArrayList<SpannableStringBuilder> = arrayListOf()
    if (text.isNotBlank() && text.contains(highLightText)) {
        text.split(highLightText).forEach {
            //正常文本
            targetSpan.add(SpanUtils().append(it).setForegroundColor(currentTextColor).create())
            //高亮文本
            targetSpan.add(SpanUtils().append(highLightText).setForegroundColor(highLightColor).create())
        }

        targetSpan.removeAt(targetSpan.size - 1)

    } else {
        targetSpan.add(SpanUtils().append(text).setForegroundColor(currentTextColor).create())
    }
    setText(TextUtils.concat(*(targetSpan.toTypedArray())))
}

//文本高亮
fun CharSequence.addHighLight(color: Int, vararg matchText: String): CharSequence {
    val style = SpannableStringBuilder(this)
    val targetRegex = StringBuilder()
    matchText.forEach {
        if (matchText.indexOf(it) < matchText.size)
            targetRegex.append(it).append("|")
    }
    val pattern = Pattern.compile(targetRegex.toString(), Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    while (matcher.find()) {
        style.setSpan(ForegroundColorSpan(color), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return style
}

//EditText快速设置删除图标显示隐藏
fun EditText.switchDelICon(clearIcon: View) {
    RxTextView.textChanges(this).subscribe {
        clearIcon.apply {
            setOnClickListener { setText("") }
        }
                .visibility = if (it.toString().isEmpty()) View.GONE else View.VISIBLE
    }
}

//EditText软键盘显示下一个
fun Context.addSoftkeyboardNext(views: List<EditText>, actionDone: (et: EditText) -> Unit) {
    (0 until views.size).forEach {
        views[it].setSingleLine()
        if (it < views.size - 1) {
            views[it].imeOptions = EditorInfo.IME_ACTION_NEXT
            views[it].nextFocusForwardId = views[it + 1].id
        } else {
            views[it].imeOptions = EditorInfo.IME_ACTION_DONE
        }
        views[it].setOnEditorActionListener { v, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> actionDone.invoke(v as EditText)
            }
            false
        }

    }
}

//editText 文本监听
fun EditText.addTextChangeListener(onChange: (s: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onChange.invoke(s?.toString() ?: return)
        }
    })
}

//editText 显示软键盘
fun EditText.showSoftInput() {
    requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
}

//recyclerView列表监听
fun RecyclerView.addOnVerticalScrollListener(onScrolledUp: (recyclerView: RecyclerView) -> Unit,
                                             onScrolledDown: (recyclerView: RecyclerView) -> Unit,
                                             onScrolledToTop: (recyclerView: RecyclerView) -> Unit,
                                             onScrolledToBottom: (recyclerView: RecyclerView) -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (!recyclerView.canScrollVertically(-1)) {
                onScrolledUp.invoke(recyclerView)
            } else if (!recyclerView.canScrollVertically(1)) {
                onScrolledDown.invoke(recyclerView)
            } else if (dy < 0) {
                onScrolledToTop.invoke(recyclerView)
            } else if (dy > 0) {
                onScrolledToBottom.invoke(recyclerView)
            }
        }
    })

}

//imageView
fun ImageView.show(path: Any?, centerCrop: Boolean = true, tr: Transformation? = null, callback: (success: Boolean) -> Unit = {}) {
    if (centerCrop) scaleType = ImageView.ScaleType.CENTER_CROP
    val creator = when (path ?: return) {
        is String -> {
            val sPath = path as String
            Picasso.with(context)
                    .load(if (TextUtils.isEmpty(sPath)) "empty" else sPath)
        }
        is Int -> Picasso.with(context)
                .load(path as Int)
        is File -> Picasso.with(context)
                .load(path as File)
        else -> {
            null
        }
    }
            ?.error(ColorDrawable(Color.parseColor("#f5f5f5")))
            ?.placeholder(ColorDrawable(Color.parseColor("#f5f5f5")))
    if (tr != null) creator?.transform(tr)
    creator?.into(this, object : Callback {
        override fun onSuccess() {
            callback.invoke(true)
        }

        override fun onError() {
            callback.invoke(false)
        }
    })

}

fun ImageView.zoom(url: String) {//网络url或者本地File路径
    if (url.isNotBlank() && drawable !is ColorDrawable) {
        Transferee.getDefault(context)
                .apply(TransferConfig.build()
                        .setSourceImageList(arrayListOf(url))
                        .setOriginImageList(arrayListOf(this))
                        .setMissDrawable(ColorDrawable(Color.parseColor("#f5f5f5")))
                        .setErrorDrawable(ColorDrawable(Color.parseColor("#f5f5f5")))
                        .setProgressIndicator(ProgressPieIndicator())
                        .setNowThumbnailIndex(0)
                        .setJustLoadHitImage(true)
                        .setImageLoader(GlideImageLoader.with(context))
                        .create())
                .show()
    }

}


//加载框
fun Context.TipDialogForLoading(title: String = getString(R.string.nucleus_loading)) = QMUITipDialog.Builder(this)
        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
        .setTipWord(title)
        .create()

//提示框
fun Context.TipDialogForTip(title: String) = QMUITipDialog.Builder(this)
        .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
        .setTipWord(title)
        .create()

//提示 :判断是 手机网络就给予
fun Context.showMobileTip(activity: Activity) {
    if (NetworkUtils.getMobileDataEnabled() && !NetworkUtils.getWifiEnabled()) {//移动网络
        val desc = Utils.getApp().resources.getText(R.string.nucleus_network_mobile_tip).toString()
        SnackbarUtils.with(activity.findViewById(android.R.id.content))
                .setMessage(desc)
                .setDuration(0)
                .showWarning()
    }
}

//启动/停止服务
fun <T : Service> Context.toggleService(target: KClass<T>, start: Boolean) {
    when (start) {
        true -> {
            stopService(Intent(this, target.java))
            startService(Intent(this, target.java))
        }
        false -> {
            stopService(Intent(this, target.java))
        }
    }
}