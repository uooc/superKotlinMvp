package uooconline.com.teacher.request.model.bean

import uooconline.com.nucleus.widget.treelist.LayoutItemType
import uooconline.com.teacher.R
import uooconline.com.teacher.request.AllList
import uooconline.com.teacher.request.AllListType
import uooconline.com.teacher.request.OneList

class AllType1(val menu: List<AllListType.Data>) : LayoutItemType {

    override fun getLayoutId(): Int = R.layout.item_all_type1

}