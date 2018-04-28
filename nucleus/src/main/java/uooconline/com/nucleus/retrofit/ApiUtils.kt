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

@file:Suppress("UNCHECKED_CAST")

package uooconline.com.nucleus.retrofit

import android.annotation.SuppressLint
import android.content.Context
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.google.gson.GsonBuilder
import github.library.parser.ExceptionParseMgr
import github.library.parser.ExceptionParser
import github.library.utils.Error
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uooconline.com.nucleus.R
import uooconline.com.nucleus.resource.*
import uooconline.com.nucleus.retrofit.exception.UoocBusinessException
import uooconline.com.nucleus.utils.ext.pref
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean


@SuppressLint("StaticFieldLeak")
object ApiUtils {
    //信鸽token
    var xgTokenFlag by pref("", XgTokenFlag)
    //版本唯一标识符
    var versionFlag by pref(AppUtils.getAppVersionCode(), VersionFlag)
    //机器唯一标识符
    var machineFlag by pref(DeviceUtils.getMacAddress() ?: "", MachineFlag)
    //机器型号
    var productFlag by pref("${DeviceUtils.getManufacturer()} ${DeviceUtils.getModel()}", ProductFlag)
    //来源唯一标识符
    var sourceFlag by pref("android", SourceFlag)
    //用户唯一标识符
    var userFlag by pref("", UserFlag)

    private lateinit var context: Context
    private var devEnvironment: Boolean = true

    fun init(context: Context, devEnvironment: Boolean) {
        ApiUtils.context = context.applicationContext
        ApiUtils.devEnvironment = devEnvironment
    }

    private val apiSingletonMap = ConcurrentHashMap<Int, Any>()

    //retrofit
    fun <T> get(apiClz: Class<T>): T {
        val key = apiClz.hashCode()
        if (apiSingletonMap[key] == null) {
            //自定义异常消息
            ExceptionParseMgr.Instance.addCustomerMessageParser { error, e ->
                if (devEnvironment) {
                    ExceptionParser.getDefaultMessage(e)
                } else {
                    when (error) {
                        Error.NetWork -> {
                            context.getString(R.string.nucleus_network_netWork)
                        }
                        Error.Internal -> {
                            context.getString(R.string.nucleus_network_internal)
                        }
                        Error.Server -> {
                            context.getString(R.string.nucleus_network_server)
                        }
                        Error.Invalid -> {
                            e.message
                        }
                        else -> {
                            context.getString(R.string.nucleus_network_unKnow)
                        }
                    }
                }
            }
            //自定义业务异常
            ExceptionParseMgr.Instance.addCustomerParser(object : ExceptionParser() {
                override fun handler(e: Throwable?, handler: IHandler): Boolean {
                    if (e != null) {
                        if (UoocBusinessException::class.java.isAssignableFrom(e.javaClass)) {
                            handler.onHandler(Error.Invalid, e.message)
                            return true
                        }
                    }
                    return false
                }
            })
            //自定义OkHttp
            val okClientBuilder = OkHttpClient.Builder().apply {
                //header
                this.addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder()
//                            .addHeader("xgTokenFlag", xgTokenFlag)//推送标识（推送）
//                            .addHeader("versionFlag", "${versionFlag}")//版本标识（更新）
//                            .addHeader("machineFlag", machineFlag)//设备标识（互斥登录）
//                            .addHeader("productFlag", productFlag)//机器型号
//                            .addHeader("sourceFlag", sourceFlag)//来源标识（Android ios）
//                            .addHeader("Authorization", "Bearer ${userFlag}")//用户验证token(操作)
                            .build())
                }
                //log
                if (devEnvironment)
                    this.addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })

            }
            //构建retrofit实例
            val api = Retrofit.Builder()
                    .baseUrl(ApiPath.getApiPath())
                    .client(okClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                            .setLenient()
                            .create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(apiClz)

            apiSingletonMap.put(key, api!!)
        }
        return apiSingletonMap[key] as T
    }

    //rxCache
    fun <T> getApiCache(apiClz: Class<T>): T {
        val key = apiClz.hashCode()
        if (apiSingletonMap[key] == null) {
            //构建rxCache实例
            val apiCache = RxCache.Builder()
                    .persistence(context.filesDir, GsonSpeaker())
                    .using(apiClz)

            apiSingletonMap.put(key, apiCache!!)
        }
        return apiSingletonMap[key] as T
    }

    var isRxCacheEvict by pref(false, NET_STATE)

}
