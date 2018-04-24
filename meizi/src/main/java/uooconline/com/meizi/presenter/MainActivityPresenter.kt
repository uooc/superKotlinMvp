package uooconline.com.meizi.presenter

import android.os.Bundle
import com.ricky.mvp_core.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.rx_cache2.DynamicKey
import uooconline.com.meizi.model.ImageItem
import uooconline.com.meizi.request.Api
import uooconline.com.meizi.request.ApiCache
import uooconline.com.nucleus.utils.ext.defPolicy_Retry
import uooconline.com.nucleus.utils.ext.parse
import uooconline.com.nucleus.utils.impl.IList

class MainActivityPresenter : BasePresenter<IList>() {
    override fun onViewCreated(view: IList, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    fun getImageList(page: Int = 1, loadMore: Boolean) {

        ApiCache.IMPL.getImageList(Api.IMPL.getImageList(Api.pageSize, page), DynamicKey(page))
                .defPolicy_Retry(this)
                .flatMap {
                    val data_ok = !it.data.error && it.data.results.isNotEmpty()
                    if (data_ok) Observable.just(it) else Observable.error(IllegalArgumentException("server business error"))
                }
                .map { it.data.results.map { ImageItem(it.url) } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view().setData(it, loadMore) },
                        { it.parse({ error, message -> view().setMessage(error, message) }) })
    }
}