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

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import uooconline.com.nucleus.utils.java.StatusBarUtil


//apply for StatusBarUtil
//使用了状态栏填充之后，整体布局会往上，需要调整布局
//在xml中需要修改的View加上：android:contentDescription="status_padding" 修改布局的padding
//android:contentDescription="status_margin" 修改布局的margin
fun Activity.applyStatusBarWhite() {
    updateContent(this, false)
}

fun Activity.applyStatusBarBlack() {
    updateContent(this, true)
}

fun Activity.applyFullScreen(full: Boolean = true) {
    if (full) {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    } else {
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

private fun updateContent(aty: Activity, black: Boolean) {

    val root: View = aty.findViewById(android.R.id.content)

    val targetRoot: ViewGroup = root as? ViewGroup ?: return

    //仅api19+有效
    QMUIStatusBarHelper.translucent(aty)
    when (black) {
        true -> QMUIStatusBarHelper.setStatusBarLightMode(aty)
        false -> QMUIStatusBarHelper.setStatusBarDarkMode(aty)
    }
    //padding
    with(arrayListOf<View>()) {
        targetRoot.findViewsWithText(this, "status_padding", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
        forEach { StatusBarUtil.setPaddingSmart(it.context, it) }
    }
    //margin
    with(arrayListOf<View>()) {
        targetRoot.findViewsWithText(this, "status_margin", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
        forEach { StatusBarUtil.setMargin(it.context, it) }
    }
}

fun Fragment.applyStatusMargin(view: View) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        StatusBarUtil.setMargin(view.context, view)
    }
}

fun Fragment.applyStatusPadding(view: View) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        StatusBarUtil.setPaddingSmart(view.context, view)
    }
}

fun Activity.applyStatusMargin(view: View) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        StatusBarUtil.setMargin(view.context, view)
    }
}

fun Activity.applyStatusPadding(view: View) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        StatusBarUtil.setPaddingSmart(view.context, view)
    }
}

fun Context.getStatusBarHeight(): Int = QMUIStatusBarHelper.getStatusbarHeight(this)