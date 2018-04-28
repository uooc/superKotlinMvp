package uooconline.com.teacher.request.model.bean

import uooconline.com.nucleus.widget.treelist.LayoutItemType
import uooconline.com.teacher.R
import uooconline.com.teacher.request.OneList

// 列表头
class Type2(val contain: OneList.Content) : LayoutItemType {

    override fun getLayoutId(): Int = R.layout.item_schedule_type2

}