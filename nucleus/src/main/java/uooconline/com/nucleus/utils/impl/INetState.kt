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

package uooconline.com.nucleus.utils.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.net.NetworkInfo
import android.os.Bundle
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.Utils
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import uooconline.com.nucleus.R
import uooconline.com.nucleus.utils.eventbus.Event
import uooconline.com.nucleus.utils.eventbus.sendEvent
import java.util.concurrent.TimeUnit

//apply for ReactiveNetwork
interface INetState {


    //filterPager ：不使用网络监测的页面
    //mobilePager : 要使用移动网络提示的页面
    fun observeNetwork(app: Application, netCallback: (isNetAvailable: Boolean) -> Unit, filterPager: ArrayList<String>) {
        val showSnackBar: (activity: Activity, str: String) -> Unit = { activity, str ->
            SnackbarUtils.with(activity.findViewById(android.R.id.content))
                    .setMessage(str)
                    .setDuration(0)
                    .showWarning()
        }

        val activityStateMap = mutableMapOf<String, Boolean>().withDefault { true }

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {


            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
            }

            @Suppress("UNCHECKED_CAST")
            @SuppressLint("MissingPermission")
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                doAsync {
                    val atyTag = activity.javaClass.simpleName
                    if (!filterPager.contains(atyTag) && activity is LifecycleProvider<*>) {
                        ReactiveNetwork
                                .observeNetworkConnectivity(activity.applicationContext)
                                .subscribeOn(Schedulers.io())
                                .bindUntilEvent(activity as LifecycleProvider<ActivityEvent>, ActivityEvent.DESTROY)//release util onDestroy
                                .compose {
                                    //compose operate ObservableSource，not consumer callBack
                                    activityStateMap[atyTag] = true
                                    it
                                }
                                .flatMap {
                                    val valid = it.isAvailable && it.state == NetworkInfo.State.CONNECTED
                                    netCallback.invoke(valid)
                                    Observable.just(it)
                                }
                                .filter { activityStateMap[atyTag] ?: true }
                                .observeOn(AndroidSchedulers.mainThread())
                                .delay(300, TimeUnit.MILLISECONDS)
                                .subscribe({
                                    when (it.state) {
                                        NetworkInfo.State.CONNECTED -> {
                                            if (it.typeName.equals("mobile", true)) {
                                                //移动网络
                                                sendEvent(Event.obtain(0xff0))
                                            }
                                        }
                                        NetworkInfo.State.DISCONNECTED -> {
                                            if (!NetworkUtils.getMobileDataEnabled()) {
                                                showSnackBar.invoke(activity, Utils.getApp().resources.getText(R.string.nucleus_network_retry_tip).toString())
                                            } else {
                                                //移动网络
                                                sendEvent(Event.obtain(0xff0))
                                            }
                                        }
                                        else -> {
                                        }
                                    }
                                })
                    }

                }
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                doAsync { activityStateMap[activity.javaClass.simpleName] = true }
            }

            override fun onActivityPaused(activity: Activity) {
                doAsync { activityStateMap[activity.javaClass.simpleName] = false }
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                doAsync { activityStateMap.remove(activity.javaClass.simpleName) }
            }
        })
    }
}