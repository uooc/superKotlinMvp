package uooconline.com.education.model.schedule.viewbinder

import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import uooconline.com.nucleus.utils.ext.show
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.nucleus.widget.treelist.TreeViewBinder
import uooconline.com.teacher.R
import uooconline.com.teacher.request.model.bean.AllType2
import uooconline.com.teacher.request.model.bean.Type2

class TypeAll2Binder() : TreeViewBinder<TypeAll2Binder.ViewHolder>() {
    override fun provideViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun getLayoutId(): Int = R.layout.item_all_type2

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
        val allType2 = node.content as AllType2
        holder.iv_pic.show(allType2.menu?.cover)
        holder.tv_desc.text= allType2.menu?.title
    }

    class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        val tv_desc: TextView = rootView.findViewById(R.id.tv_desc)
        val iv_pic: ImageView = rootView.findViewById(R.id.iv_pic)

    }



    override fun onPaddingHeight(holder: ViewHolder, height: Int) {
        super.onPaddingHeight(holder, height)
        holder.itemView.setPadding(0, 0, 0, 0)
    }
}