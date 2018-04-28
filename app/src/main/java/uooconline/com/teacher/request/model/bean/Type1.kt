package uooconline.com.teacher.request.model.bean

import uooconline.com.nucleus.widget.treelist.LayoutItemType
import uooconline.com.teacher.R
import uooconline.com.teacher.request.OneList

// 首頁ONE 第一個條目
class Type1(val firstContain: OneList.Content) : LayoutItemType {

    override fun getLayoutId(): Int = R.layout.item_schedule_type1

}