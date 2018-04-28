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

package uooconline.com.teacher.request

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import uooconline.com.nucleus.retrofit.ApiUtils
import uooconline.com.teacher.request.model.TextItem


interface Api {
    companion object {
        private val APIConfig = mapOf(
                Pair("pageSize", 10),
                Pair("startOffset", 1))
                .withDefault { 0 }

        val IMPL: Api = ApiUtils.get(Api::class.java)
        val pageSize by APIConfig
        val startOffset by APIConfig
    }

    @GET("https://jsonplaceholder.typicode.com/posts")
    fun getTexts(): Observable<List<TextItem>>

    /**
     *     http://v3.wufazhuce.com:8000/api
     *     /onelist/idlist/
     *     ?channel=wdj&
     *     version=4.0.2&
     *     uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&
     *     platform=android
     */
    //获取最新 idlist
    @GET("/api/onelist/idlist")
    fun idlist(@Query("uuid") uuid: String,
               @Query("channel") channel: String = "mi",
               @Query("version") version: String = "4.0.2",
               @Query("platform") platform: String = "android"): Observable<OneIdlist>

    //获取 onelist
    @GET("/api/onelist/{id}/0")
    fun onelist(@Query("id") id: String,
                @Query("uuid") uuid: String,
               @Query("channel") channel: String = "mi",
               @Query("version") version: String = "4.0.2",
               @Query("platform") platform: String = "android"): Observable<OneList>
    /**
     * 取特定日期的 one 信息
     * 首页：http://v3.wufazhuce.com:8000/api/hp/bymonth/ + yyyy-MM-dd + %2000:00:00?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android
     * @param date yyyy-MM-dd
     */
    @GET("/hp/bymonth/{date}%2000:00:00")
    fun bymonth(@Query("date") id: String,
                @Query("uuid") uuid: String,
                @Query("channel") channel: String = "mi",
                @Query("version") version: String = "4.0.2",
                @Query("platform") platform: String = "android"): Observable<Bymonth>
    //http://v3.wufazhuce.com:8000/api/channel/one/0/深圳?channel=mi&sign=b0070db1a6072d597b6cfa3154edb26a&version=4.5.3&uuid=00000000-1524-4a51-a999-8a9f486e1e63&platform=android
    @GET("/api/channel/one/{date}/{location}")
    fun onelisthome(@Query("date") date: String,
                    @Query("uuid") uuid: String,
                @Query("date") location: String="深圳",
                @Query("channel") channel: String = "mi",
                @Query("version") version: String = "4.0.2",
                @Query("platform") platform: String = "android"): Observable<OneList>
}
