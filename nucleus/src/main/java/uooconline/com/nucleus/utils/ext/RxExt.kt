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
import com.blankj.utilcode.util.ToastUtils
import com.ricky.mvp_core.base.BasePresenter
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import github.library.parser.ExceptionParseMgr
import github.library.utils.Error
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import uooconline.com.nucleus.R
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.Succeed
import java.io.File
import java.util.concurrent.TimeUnit

//net error parse
fun Throwable.parse(iHandler: (error: Error, message: String) -> Unit) {
    ExceptionParseMgr.Instance.parseException(this, iHandler)
}

//bind BehaviorProcessor<Boolean>
fun <T> Observable<T>.bindToBehavior(bp: BehaviorProcessor<Boolean>): Observable<T> = this
        .takeUntil(bp.toObservable().skipWhile { it })

//rx default mainThread
fun <T> Observable<T>.defThread(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


//retry when error
fun <T> Observable<T>.defRetry(count: Int = 10, period: Long = 2): Observable<T> {
    var currentCount: Int = 0
    return this
            .observeOn(Schedulers.io())
            .retryWhen {
                it
                        .zipWith(Observable.range(1, count + 1), BiFunction<Throwable, Int, Throwable> { t1, t2 ->
                            currentCount = t2
                            t1
                        })
                        .flatMap {
                            if (ExceptionParseMgr.Instance.isMatchException(it) == Error.NetWork && currentCount < count + 1) {
                                Observable.timer(period, TimeUnit.SECONDS)
                            } else {
                                Observable.error(it)
                            }
                        }
            }
}

//【仅需要网络异常时重试才使用此策略】通常是列表，详情，网络异常时有限且间隔的重新发起请求
//【请注意第二个参数】：若多个不同请求同时发起时，必须传入标识唯一值
//因为重试请求一直在发起，所以需要绑定外部的BehaviorProcessor，避免新请求执行时，重试仍然在执行，本项目的BasePresenter提供一个map缓存
//其它项目需要外部使用ob.bindToBehavior(bp)
fun <T> Observable<T>.defPolicy_Retry(lifecycle: LifecycleProvider<*>, requestFlag: String = "mvpro"): Observable<T> {
    val baseP = lifecycle as? BasePresenter<*>
    val o = when (baseP) {
        null -> this.defThread().defRetry()
        else -> this.defThread().defRetry().bindToBehavior(baseP.mBehaviorMap[requestFlag])
    }
    return if (lifecycle is RxAppCompatActivity) {
        o.bindUntilEvent(lifecycle as LifecycleProvider<ActivityEvent>, ActivityEvent.DESTROY)
    } else {
        o.bindUntilEvent(lifecycle as LifecycleProvider<FragmentEvent>, FragmentEvent.DESTROY_VIEW)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Observable<T>.defPolicy(lifecycle: LifecycleProvider<*>): Observable<T> {
    val o = this.defThread()
    return if (lifecycle is RxAppCompatActivity) {
        o.bindUntilEvent(lifecycle as LifecycleProvider<ActivityEvent>, ActivityEvent.DESTROY)
    } else {
        o.bindUntilEvent(lifecycle as LifecycleProvider<FragmentEvent>, FragmentEvent.DESTROY_VIEW)
    }
}

@Deprecated("")
fun BasePresenter<*>.getBehavior(behavior: BehaviorProcessor<Boolean>?): BehaviorProcessor<Boolean> {
    //set last behavior
    behavior?.onNext(false)
    //reset behavior
    return BehaviorProcessor.create()
}

//rxPermissions【https://ww4.sinaimg.cn/large/6a195423jw1ezwpc11cs0j20hr0majwm.jpg】
fun Activity.requestAllPermission(rxPermissions: RxPermissions,
                                  success: () -> Unit,
                                  vararg permissions: String) {
    rxPermissions.request(*permissions)
            .subscribe {
                if (it) {
                    success.invoke()
                } else {
                    ToastUtils.showShort(getString(R.string.nucleus_check_permission))
                }
            }
}

//error: (denyName: String) -> Unit = { ToastUtils.showShortSafe("$it permission request fail") }
fun Activity.requestEachPermission(rxPermissions: RxPermissions,
                                   success: () -> Unit,
                                   vararg permissions: String) {
    rxPermissions.requestEach(*permissions)
            .subscribe {
                if (it.granted) {
                    success.invoke()
                } else if (it.shouldShowRequestPermissionRationale) {
                    ToastUtils.showShort("${it.name} ${getString(R.string.nucleus_permission_request_error)}")
                } else {
                    ToastUtils.showShort("${it.name} ${getString(R.string.nucleus_permission_request_error)}")
                }
            }
}
//取url最后一个斜杠为文件名做下载至缓存目录
fun Context.downLoadFile(url: String, success: (file: File) -> Unit) {
    val fileName = if (url.lastIndexOf("/") != -1) url.substring(url.lastIndexOf("/") + 1) else "tmp"
    val m = Mission(url, fileName, cacheDir.absolutePath)
    RxDownload
            .create(m)
            .subscribe {
                when (it) {
                    is Succeed -> {
                        RxDownload.file(m).observeOn(AndroidSchedulers.mainThread()).subscribe {
                            success.invoke(it)
                        }
                    }
                    else -> {
                        RxDownload.start(m).subscribe {}
                    }
                }
            }
}

