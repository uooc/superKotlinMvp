package uooconline.com.teacher.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import uooconline.com.nucleus.utils.ext.show
import uooconline.com.teacher.R
import uooconline.com.teacher.request.AllListType


class AllTpeyAdapter : CommonListAdapter<AllListType.Data>(R.layout.item_all_type_iamgeview) {
    override fun convert(helper: BindHolder, item: AllListType.Data) {
        var iv = helper.view.findViewById<ImageView>(R.id.iv_pic)
        var tv_title = helper.view.findViewById<TextView>(R.id.tv_title)
        val lp = helper.view.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            if(item.with_x == 2){
                lp.width =  lp.width * 2
            }
            val flexboxLp = lp as FlexboxLayoutManager.LayoutParams
            flexboxLp.flexGrow = 3.0f
        }
        iv.show(item.cover)
        tv_title.text = item.title

    }
}