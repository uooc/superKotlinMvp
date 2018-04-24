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
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.LifeCache
import io.rx_cache2.Reply
import uooconline.com.meizi.request.model.ImageRequest
import uooconline.com.nucleus.retrofit.ApiUtils
import java.util.concurrent.TimeUnit

interface ApiCache {
    companion object {
        val IMPL: ApiCache = ApiUtils.getApiCache(ApiCache::class.java)
        val NO_CACHE: Boolean = ApiUtils.isRxCacheEvict
    }

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getImageList(resObservable: Observable<ImageRequest.Res>, url: DynamicKey, evictDynamicKey: EvictDynamicKey = EvictDynamicKey(NO_CACHE)): Observable<Reply<ImageRequest.Res>>

}