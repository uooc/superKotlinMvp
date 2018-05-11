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
import uooconline.com.teacher.request.model.bean.Type2

class Type2Binder() : TreeViewBinder<Type2Binder.ViewHolder>() {
    override fun provideViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun getLayoutId(): Int = R.layout.item_schedule_type2

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
        val item = node.content as Type2
        var title = "故事"
        var subtitle = ""
        when(item.contain.content_type){
            "0"-> title = ""
            "1"-> {
                title = if(item.contain.tag_list!= null && item.contain.tag_list.isNotEmpty() && !isEmpty(item.contain.tag_list[0].title)){
                    item.contain.tag_list[0].title ?:"阅读"
                }else{
                    "阅读"
                }
            }
            "2"-> title = "連載"
            "3"-> title = "問答"
            "4"-> title = "音乐"
            "5"-> title = "影视"
            "8"-> title = "電台"
        }
        holder.tv_title.text =  "-" +title +"-"
        holder.tv_author.text = item.contain.title
        holder.tv_photographer.text = "文 / "+item.contain.author.user_name
        holder.tv_mood.text = item.contain.forward
        when(item.contain.content_type){
            "4"->{
                holder.iv_mood.visibility = View.GONE
                holder.iv_head.visibility = View.VISIBLE
                holder.iv_play.visibility = View.VISIBLE
                holder.iv_head.show(item.contain.img_url)
            }
            else ->{
                holder.iv_mood.visibility = View.VISIBLE
                holder.iv_head.visibility = View.GONE
                holder.iv_play.visibility = View.GONE
                holder.iv_mood.show(item.contain.img_url)
            }
        }
    }

    class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        val tv_title: TextView = rootView.findViewById(R.id.tv_title)
        val tv_author: TextView = rootView.findViewById(R.id.tv_author)
        val tv_photographer: TextView = rootView.findViewById(R.id.tv_photographer)
        val tv_mood: TextView = rootView.findViewById(R.id.tv_mood)
        val iv_mood: ImageView = rootView.findViewById(R.id.iv_mood)
        val iv_play: ImageView = rootView.findViewById(R.id.iv_music_play)
        val iv_head: QMUIRadiusImageView = rootView.findViewById(R.id.iv_head)

    }



    override fun onPaddingHeight(holder: ViewHolder, height: Int) {
        super.onPaddingHeight(holder, height)
        holder.itemView.setPadding(0, 0, 0, 0)
    }
}