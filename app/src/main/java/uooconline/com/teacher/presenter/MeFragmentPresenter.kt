package uooconline.com.teacher.presenter

import android.os.Bundle
import android.text.TextUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.TimeUtils
import com.ricky.mvp_core.base.BasePresenter
import com.ricky.mvp_core.base.interfaces.IView
import io.reactivex.android.schedulers.AndroidSchedulers
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
import uooconline.com.teacher.ui.view.IMeFragment
import uooconline.com.teacher.ui.view.IOneFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Create date: 2018/4/26 0026 on 15:00
 * Describe：
 * Author： GenGeGe
 */
class MeFragmentPresenter: BasePresenter<IMeFragment>() {

    var mUUID = "00000000-1524-4a51-a999-8a9f486e1e63"
    var mTitle:String?=""
    var mSuberTitle:String?=""

    override fun onViewCreated(view: IMeFragment, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

}