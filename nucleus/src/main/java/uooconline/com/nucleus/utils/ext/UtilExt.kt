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
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.support.annotation.StyleRes
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import org.jetbrains.anko.activityManager
import uooconline.com.nucleus.R

//Fragment
fun Fragment.toast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    when (length) {
        Toast.LENGTH_SHORT -> ToastUtils.showShort(msg ?: return)
        Toast.LENGTH_LONG -> ToastUtils.showLong(msg ?: return)
    }
}

//Activity
fun Activity.toast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    if (TextUtils.isEmpty(msg)) return
    when (length) {
        Toast.LENGTH_SHORT -> {
            ToastUtils.showShort(msg ?: return)
        }
        Toast.LENGTH_LONG -> {
            ToastUtils.showLong(msg ?: return)
        }
    }
}

//Context
fun Context.toast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    when (length) {
        Toast.LENGTH_SHORT -> {
            ToastUtils.showShort(msg ?: return)
        }
        Toast.LENGTH_LONG -> {
            ToastUtils.showLong(msg ?: return)
        }
    }
}


fun Activity.hideKeyboard(): Boolean {
    val view = currentFocus
    view?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
}

fun Context.showTipDialog(title: String, content: String, vararg btn: DialogBtnItem): QMUIDialog.MessageDialogBuilder {
    val builder = QMUIDialog.MessageDialogBuilder(this)
            .setTitle(if (title.isBlank()) getString(R.string.nucleus_dialog_normal_title) else title)
            .setMessage(content)

    btn.forEach {
        builder.addAction(0, it.message, it.prop, { dialog, _ ->
            it.callback.invoke()
            dialog.dismiss()
        })
    }
    builder.show()
    return builder
}

fun Context.showTipDialog(title: String, content: String, @StyleRes anim: Int = -1, vararg btn: DialogBtnItem): QMUIDialog {
    val builder = QMUIDialog.MessageDialogBuilder(this)
            .setTitle(if (title.isBlank()) getString(R.string.nucleus_dialog_normal_title) else title)
            .setMessage(content)

    btn.forEach {
        builder.addAction(0, it.message, it.prop, { dialog, _ ->
            it.callback.invoke()
            dialog.dismiss()
        })
    }
    val c: QMUIDialog
    if (anim != -1) {
        c = builder.create()
        c.window.setWindowAnimations(anim)
        c.show()
    } else {
        c = builder.show()
    }
    return c
}

class DialogBtnItem(val message: String,
                    val callback: () -> Unit,
                    @QMUIDialogAction.Prop val prop: Int = QMUIDialogAction.ACTION_PROP_NEUTRAL)

//Application
fun Application.getRunningProcessList(): MutableList<ActivityManager.RunningAppProcessInfo>? {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return am.runningAppProcesses
}

fun Application.getProcessName(): String? {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return am.runningAppProcesses
            ?.find { it.pid == android.os.Process.myPid() }
            ?.processName
}

fun Application.killCurrentProcess() {
    android.os.Process.killProcess(android.os.Process.myPid())
}

//Context
fun Context.isAppOnForeground(): Boolean {
    val appProcesses = activityManager.runningAppProcesses
    appProcesses?.filter { it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && it.processName == packageName }?.forEach { return true }
    return false
}

//gson
inline fun <reified T> Gson.fromJson(json: String): T {
    return fromJson(json, T::class.java)
}

//log
inline fun <reified T> T.L(message: Any, tag: String = T::class.java.simpleName) {
    Log.d(tag, message.toString())
}

