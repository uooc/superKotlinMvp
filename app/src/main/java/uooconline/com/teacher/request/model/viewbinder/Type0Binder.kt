package uooconline.com.education.model.schedule.viewbinder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.nucleus.widget.treelist.TreeViewBinder
import uooconline.com.teacher.R
import uooconline.com.teacher.request.model.bean.Type0

class Type0Binder() : TreeViewBinder<Type0Binder.ViewHolder>() {
    override fun provideViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun getLayoutId(): Int = R.layout.item_schedule_type0

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
        val item = node.content as Type0
        holder.mTitle.text = "一个 VOL."+item.menu
    }

    class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        val mTitle: TextView = rootView.findViewById(R.id.tv_title)
        val mArrow: ImageView = rootView.findViewById(R.id.arrow_pic)

        fun toggleRotateArrow(isInsert: Boolean) {
            mArrow.animate()?.rotationBy(if (isInsert) 180f else -180f)?.start()
        }
    }



    override fun onPaddingHeight(holder: ViewHolder, height: Int) {
        super.onPaddingHeight(holder, height)
        holder.itemView.setPadding(0, 0, 0, 0)
    }
}