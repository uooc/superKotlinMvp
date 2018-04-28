@file:Suppress("UNCHECKED_CAST")

package uooconline.com.nucleus.retrofit.exception

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.rx_cache2.Reply
import uooconline.com.nucleus.resource.ACCOUNT_CONFILICT
import uooconline.com.nucleus.resource.ACCOUNT_UNLOGIN
import uooconline.com.nucleus.resource.CERTIFI_AUTHING
import uooconline.com.nucleus.resource.CERTIFI_UN_AUTH
import uooconline.com.nucleus.retrofit.request.BaseRequest
import uooconline.com.nucleus.utils.eventbus.Event
import uooconline.com.nucleus.utils.eventbus.sendEvent

/**
 * 带业务性质的网络过滤
 */
class UoocTransformer<T> : ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        val a = upstream.flatMap {
            when (it) {
            //normal
                is BaseRequest<*> -> {
                    val b = it as BaseRequest<*>
                    if (b.res == 0) {
                        Observable.just(it)
                    } else {
                        handleError(b)
                    }
                }
            //rxCache
                is Reply<*> -> {
                    val b = (it as Reply<*>).data as BaseRequest<*>
                    if (b.res == 0) {
                        Observable.just(it)
                    } else {
                        handleError(b)
                    }
                }
                else -> {
                    Observable.just(it)
                }
            }
        }
        return a as ObservableSource<T>
    }

    private fun handleError(b: BaseRequest<*>): Observable<Any?>? {
        sendEvent(Event.obtain(when (b.res) {
            420 -> ACCOUNT_CONFILICT//冲突
            401 -> ACCOUNT_UNLOGIN//未登录
            103 -> CERTIFI_UN_AUTH//未认证
            104 -> CERTIFI_AUTHING//正在认证中
            else -> -1
        }, b.msg))
        return Observable.error(UoocBusinessException(b.msg, b.res))
    }

}