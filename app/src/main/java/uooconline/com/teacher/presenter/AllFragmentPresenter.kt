package uooconline.com.teacher.presenter

import android.os.Bundle
import com.ricky.mvp_core.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import io.rx_cache2.DynamicKey
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.EvictProvider
import uooconline.com.nucleus.retrofit.ApiUtils
import uooconline.com.nucleus.retrofit.exception.UoocTransformer
import uooconline.com.nucleus.utils.ext.L
import uooconline.com.nucleus.utils.ext.bindToBehavior
import uooconline.com.nucleus.utils.ext.defPolicy
import uooconline.com.nucleus.utils.ext.parse
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.teacher.R
import uooconline.com.teacher.request.AllList
import uooconline.com.teacher.request.AllListType
import uooconline.com.teacher.request.Api
import uooconline.com.teacher.request.ApiCache
import uooconline.com.teacher.request.model.bean.AllType0
import uooconline.com.teacher.request.model.bean.AllType1
import uooconline.com.teacher.request.model.bean.AllType2
import uooconline.com.teacher.ui.view.IAllFragment
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Create date: 2018/4/26 0026 on 15:00
 * Describe：
 * Author： GenGeGe
 */
class AllFragmentPresenter: BasePresenter<IAllFragment>() {

    var mUUID = "00000000-1524-4a51-a999-8a9f486e1e63"
    var mTitle:String?=""
    var mSuberTitle:String?=""

    override fun onViewCreated(view: IAllFragment, arguments: Bundle?, savedInstanceState: Bundle?) {
    }


    var source: CopyOnWriteArrayList<TreeNode<*>> = CopyOnWriteArrayList()
    fun getOneList( success: (d: List<TreeNode<*>>) -> Unit, failure: () -> Unit) {
        Observable.just(source)
                .doOnSubscribe {
                    source.clear()
                }
                .defPolicy(this)
                .observeOn(Schedulers.io())
                .flatMap {
                    //轮播图 和  内容
                    Observables.combineLatest(
                            ApiCache.IMPL.allList(Api.IMPL.allList("3",mUUID) .compose(UoocTransformer()), DynamicKey("app_all_type0"),
                                    EvictDynamicKey(ApiUtils.isRxCacheEvict)),
//                            ApiCache.IMPL.allList(Api.IMPL.allList("5",mUUID) , DynamicKey("app_all_type1"),
//                                    EvictDynamicKey(ApiUtils.isRxCacheEvict)),
                            ApiCache.IMPL.allList(Api.IMPL.allList("4",mUUID) , DynamicKey("app_all_type2"),
                                    EvictDynamicKey(ApiUtils.isRxCacheEvict)))

                }.flatMap {
                    source.add(TreeNode(AllType0(it.first.data.data!!)))
                    var lAllList  = arrayListOf<AllListType.Data>().apply {
                        add(AllListType.Data(0, R.mipmap.cat_1,1,"阅读"))
                        add(AllListType.Data(1, R.mipmap.cat_2,1,"阅读"))
                        add(AllListType.Data(2, R.mipmap.cat_3,2,"影視"))
                        add(AllListType.Data(3, R.mipmap.cat_4,1,"電台"))
                        add(AllListType.Data(4, R.mipmap.cat_5,1,"音乐"))
                        add(AllListType.Data(5, R.mipmap.cat_6,1,"阅读"))
                        add(AllListType.Data(6, R.mipmap.cat_7,1,"阅读"))
                    }
                    source.add(TreeNode(AllType1(lAllList)))
                    it.second.data.data?.forEach {
                        source.add(TreeNode(AllType2(it)))
                    }
                    Observable.just(source)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            success.invoke(source)
                        }, {
                    failure.invoke()
                })
    }

    //所有item展开
    fun expandAllItem(datas: List<TreeNode<*>>) {
        for (i in 0 until datas.size) {
            val item = datas[i]
            if (!item.isLeaf) {
                item.toggle()
                expandAllItem(item.childList)
            }
        }
    }
}