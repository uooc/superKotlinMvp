package uooconline.com.teacher.presenter

import android.os.Bundle
import com.ricky.mvp_core.base.BasePresenter
import io.reactivex.Observable
import uooconline.com.nucleus.retrofit.exception.UoocTransformer
import uooconline.com.nucleus.utils.ext.defPolicy
import uooconline.com.nucleus.utils.ext.parse
import uooconline.com.nucleus.utils.impl.IList
import uooconline.com.teacher.request.Api
import uooconline.com.teacher.request.ApiCache
import java.util.concurrent.TimeUnit

class MainActivityPresenter : BasePresenter<IList>() {
    override fun onViewCreated(view: IList, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    fun getDatas(view: IList, loadMore: Boolean = false) {
        ApiCache.IMPL.getTexts(Api.IMPL.getTexts())
                .defPolicy(this)
                .subscribe({
                    view.setData(it.data, loadMore)
                }, {
                    it.parse({ error, message -> view().setMessage(error, message) })
                })
    }
}