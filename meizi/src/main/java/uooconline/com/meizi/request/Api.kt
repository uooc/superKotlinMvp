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

package uooconline.com.meizi.request

import io.reactivex.Observable
import retrofit2.http.*
import uooconline.com.meizi.request.model.ImageRequest
import uooconline.com.nucleus.retrofit.ApiUtils
import uooconline.com.nucleus.retrofit.request.BaseRequest


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


    @GET("http://gank.io/api/data/福利/{size}/{page}")
    fun getImageList(@Path("size") size: Int, @Path("page") page: Int): Observable<ImageRequest.Res>


}
