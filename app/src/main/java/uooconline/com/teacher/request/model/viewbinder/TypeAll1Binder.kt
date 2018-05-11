package uooconline.com.education.model.schedule.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.google.android.flexbox.*
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.nucleus.widget.treelist.TreeViewBinder
import uooconline.com.teacher.R
import uooconline.com.teacher.request.model.bean.AllType1
import uooconline.com.teacher.ui.adapter.AllTpeyAdapter


class TypeAll1Binder() : TreeViewBinder<TypeAll1Binder.ViewHolder>() {
    override fun provideViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun getLayoutId(): Int = R.layout.item_all_type1

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
        val allType1 = node.content as AllType1
        val layoutMger = FlexboxLayoutManager(holder.recyclerView.context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        holder.recyclerView.apply {
           layoutManager  = layoutMger
           val ad = AllTpeyAdapter()
            ad.setNewData(allType1.menu)
            adapter = ad
        }
    }

    class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        var recyclerView = rootView.findViewById(R.id.mRecyclerView) as RecyclerView
    }



    override fun onPaddingHeight(holder: ViewHolder, height: Int) {
        super.onPaddingHeight(holder, height)
        holder.itemView.setPadding(0, 0, 0, 0)
    }
}