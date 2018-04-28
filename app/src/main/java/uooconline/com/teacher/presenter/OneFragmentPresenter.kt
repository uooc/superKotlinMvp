package uooconline.com.teacher.presenter

import android.os.Bundle
import android.text.TextUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.TimeUtils
import com.ricky.mvp_core.base.BasePresenter
import com.ricky.mvp_core.base.interfaces.IView
import org.jetbrains.anko.collections.forEachByIndex
import uooconline.com.nucleus.utils.ext.defPolicy
import uooconline.com.nucleus.utils.ext.parse
import uooconline.com.nucleus.utils.impl.IList
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.teacher.request.Api
import uooconline.com.teacher.request.ApiCache
import uooconline.com.teacher.request.model.bean.Type0
import uooconline.com.teacher.request.model.bean.Type1
import uooconline.com.teacher.request.model.bean.Type2
import uooconline.com.teacher.request.model.bean.Type3
import uooconline.com.teacher.ui.view.IOneFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Create date: 2018/4/26 0026 on 15:00
 * Describe：
 * Author： GenGeGe
 */
class OneFragmentPresenter: BasePresenter<IOneFragment>() {

    var mUUID = "00000000-1524-4a51-a999-8a9f486e1e63"

    override fun onViewCreated(view: IOneFragment, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    fun getIdsList() {
        ApiCache.IMPL.idlist(Api.IMPL.idlist(mUUID))
                .defPolicy(this)
                .subscribe({
                    view().obtainIdList(it.data.data)
                }, {
                    it.parse({ _, message -> view().setMessage( message) })
                })
    }

    var source: CopyOnWriteArrayList<TreeNode<*>> = CopyOnWriteArrayList()
    fun getOneList(view: IView, date:String?, success: (d: List<TreeNode<*>>) -> Unit, failure: () -> Unit) {
        if(TextUtils.isEmpty(date))
            return
        source.clear()
        ApiCache.IMPL.onelisthome(Api.IMPL.onelisthome(date!!,mUUID))
                .defPolicy(this)
                .map {
                    if(it.data?.data?.content_list != null && it.data?.data?.content_list?.size!! > 0){
                        it.data?.data?.content_list!!.forEachIndexed { index, content ->
                            if(index == 0){
                                val typefirst = TreeNode(Type1(content))
                                source.add(typefirst)
                                if(it.data?.data?.menu != null){
                                    val typemenu = TreeNode(Type0(it.data?.data?.menu!!.vol))
                                    it.data?.data?.menu?.list?.forEachIndexed { _, x ->
                                        typemenu.addChild(TreeNode(Type3(x)))
                                    }
                                    source.add(typemenu)
                                }
                            }else{
                                val typeOther = TreeNode(Type2(content))
                                source.add(typeOther)
                            }
                        }
                    }
                    source
                }.map {
                    expandAllItem(it)
                    source
                }
                .subscribe({
                    success.invoke(source)
                }, {
                    failure.invoke()
                    it.parse({ _, message -> view().setMessage( message) })
                })
    }

    /**
     * 获取特定日期的 one 信息
     */
    fun bymonth() {
         var DEFAULT_FORMAT: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date =TimeUtils.date2String(TimeUtils.getNowDate(),DEFAULT_FORMAT)
       Api.IMPL.bymonth(date!!,mUUID)
                .defPolicy(this)
                .subscribe({
                    view().obtainBymonth(it.data)
                }, {
                    it.parse({ _, message -> view().setMessage( message) })
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