package uooconline.com.teacher.request.model.bean

import uooconline.com.nucleus.widget.treelist.LayoutItemType
import uooconline.com.teacher.R
import uooconline.com.teacher.request.AllList
import uooconline.com.teacher.request.OneList

// 列表头
class AllType0(val menu: List<AllList.Data>) : LayoutItemType {

    override fun getLayoutId(): Int = R.layout.item_all_type0

}