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

}
