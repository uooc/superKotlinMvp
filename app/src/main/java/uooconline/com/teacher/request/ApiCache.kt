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
import io.rx_cache2.*
import uooconline.com.nucleus.retrofit.ApiUtils
import uooconline.com.teacher.request.model.TextItem
import java.util.concurrent.TimeUnit

interface ApiCache {
    companion object {
        val IMPL: ApiCache = ApiUtils.getApiCache(ApiCache::class.java)
        val NO_CACHE: Boolean = ApiUtils.isRxCacheEvict
    }

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    fun getTexts(resObservable: Observable<List<TextItem>>, evictProvider: EvictProvider = EvictProvider(NO_CACHE)): Observable<Reply<List<TextItem>>>

}