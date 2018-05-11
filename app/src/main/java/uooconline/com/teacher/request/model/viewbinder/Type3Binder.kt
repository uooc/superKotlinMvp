package uooconline.com.education.model.schedule.viewbinder

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.nucleus.widget.treelist.TreeViewBinder
import uooconline.com.teacher.R
import uooconline.com.teacher.request.model.bean.Type2
import uooconline.com.teacher.request.model.bean.Type3

class Type3Binder() : TreeViewBinder<Type3Binder.ViewHolder>() {
    override fun provideViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun getLayoutId(): Int = R.layout.item_schedule_type3

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
        val item = node.content as Type3
        var typeName = ""
        when(item.contain.content_type){
            "1"->{
                typeName = if(item.contain.tag != null &&!TextUtils.isEmpty(item.contain.tag.title)){
                    item.contain.tag.title!!
                }else{
                    "阅读"
                }
            }
            "2"-> typeName = "連載"
            "3"-> typeName = "問答"
            "4"-> typeName = "音乐"
            "5"-> typeName = "影视"
            "8"-> typeName = "電台"

        }
        holder.tv_tips.text = typeName
        holder.tv_contian.text = item.contain.title
    }

    class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        val tv_tips: TextView = rootView.findViewById(R.id.tv_tips)
        val tv_contian: TextView = rootView.findViewById(R.id.tv_contian)

    }



    override fun onPaddingHeight(holder: ViewHolder, height: Int) {
        super.onPaddingHeight(holder, height)
        holder.itemView.setPadding(0, 0, 0, 0)
    }
}