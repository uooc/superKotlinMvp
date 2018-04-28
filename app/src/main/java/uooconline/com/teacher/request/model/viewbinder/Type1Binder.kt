package uooconline.com.education.model.schedule.viewbinder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import uooconline.com.nucleus.utils.ext.show
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.nucleus.widget.treelist.TreeViewBinder
import uooconline.com.teacher.R
import uooconline.com.teacher.request.model.bean.Type0
import uooconline.com.teacher.request.model.bean.Type1

class Type1Binder() : TreeViewBinder<Type1Binder.ViewHolder>() {
    override fun provideViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun getLayoutId(): Int = R.layout.item_schedule_type1

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
        val item = node.content as Type1
        holder.iv_mood.show(item.firstContain.img_url)
    }

    class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        val iv_mood: ImageView = rootView.findViewById(R.id.iv_mood)
        val tv_mood: TextView = rootView.findViewById(R.id.tv_mood)
        val tv_author: TextView = rootView.findViewById(R.id.tv_author)
        val tv_photographer: TextView = rootView.findViewById(R.id.tv_photographer)

    }



    override fun onPaddingHeight(holder: ViewHolder, height: Int) {
        super.onPaddingHeight(holder, height)
        holder.itemView.setPadding(0, 0, 0, 0)
    }
}