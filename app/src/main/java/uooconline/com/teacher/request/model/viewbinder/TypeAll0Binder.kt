package uooconline.com.education.model.schedule.viewbinder

import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.blankj.utilcode.util.SizeUtils
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import uooconline.com.nucleus.utils.ext.show
import uooconline.com.nucleus.widget.banner.UltraViewPager
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.nucleus.widget.treelist.TreeViewBinder
import uooconline.com.teacher.R
import uooconline.com.teacher.request.model.bean.AllType0

class TypeAll0Binder() : TreeViewBinder<TypeAll0Binder.ViewHolder>() {
    override fun provideViewHolder(itemView: View): ViewHolder = ViewHolder(itemView)

    override fun getLayoutId(): Int = R.layout.item_all_type0

    override fun bindView(holder: ViewHolder, position: Int, node: TreeNode<*>) {
        val allType0 = node.content as AllType0
        val mBanner = holder.mBanner
        mBanner.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
        mBanner.initIndicator()
        mBanner.indicator
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.parseColor("#0b99ff"))
                .setNormalColor(Color.WHITE)
                .setRadius(SizeUtils.dp2px(3f))
                .setIndicatorPadding(SizeUtils.dp2px(7f))
                .setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
                .setMargin(0, 0, 0, SizeUtils.dp2px(7f))
                .build()
        mBanner.setInfiniteLoop(true)
        mBanner.setAutoScroll(4000)
        mBanner.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
            override fun getCount(): Int = allType0.menu.size
            override fun instantiateItem(container: ViewGroup, position: Int): Any {

                val item = QMUIRadiusImageView(container.context)
                        .apply {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            cornerRadius = SizeUtils.dp2px(5f)
                            isCircle = false
                            selectedMaskColor = Color.parseColor("#EEEEF0")
                        }
                container.addView(item, LinearLayout.LayoutParams(-1, -1))
                item.show(allType0.menu[position].cover)
//                val bean: AdvertisementItem = allType0.menu[position]
//                item.setOnClickListener {
//                    if (!TextUtils.isEmpty(bean.app_h5_url)){
//                        mBanner.context.MobEvent(class_table_ad)
//                        mBanner.context.router(RouterImpl.CommonWebActivity, Pair("title", bean.title), Pair("url", bean.app_h5_url), Pair("useUrlTitle", "false"), Pair("eventId", class_table_ad_time))
//                    }
//                }
                return item
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container?.removeView(`object` as View)
            }

        }
    }

    class ViewHolder(rootView: View) : TreeViewBinder.ViewHolder(rootView) {
        val mBanner: UltraViewPager = rootView.findViewById(R.id.mBanner)
    }
    override fun onPaddingHeight(holder: ViewHolder, height: Int) {
        super.onPaddingHeight(holder, height)
        holder.itemView.setPadding(0, 0, 0, 0)
    }
}