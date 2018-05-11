package uooconline.com.teacher.ui.view

import com.ricky.mvp_core.base.interfaces.IView
import uooconline.com.nucleus.utils.impl.IList
import uooconline.com.teacher.request.Bymonth
import uooconline.com.teacher.request.OneIdlist
import uooconline.com.teacher.request.OneList

interface IAllFragment : IView {
    fun obtainBymonth(content: Bymonth.Data?)= Unit

    fun obtainTitle(title:String,subtitle:String) = Unit
}