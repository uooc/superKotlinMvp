package uooconline.com.nucleus.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import uooconline.com.nucleus.R
import uooconline.com.nucleus.BR

abstract class BaseMulListAdapter<B : MultiItemEntity> : BaseMultiItemQuickAdapter<B, BaseMulListAdapter.BindHolder>(null) {

    final override fun convert(helper: BindHolder, item: B) {

        onConvertBindBefore(helper, item)

        //默认数据绑定的变量的id是【item】
        val binding = helper.getBinding()
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()

        onConvertBindAfter(helper, item)
    }

    open fun onConvertBindBefore(helper: BindHolder, item: MultiItemEntity) {}
    open fun onConvertBindAfter(helper: BindHolder, item: MultiItemEntity) {}

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return view
    }

    class BindHolder(val view: View) : BaseViewHolder(view) {
        fun getBinding(): ViewDataBinding {
            return itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
        }
    }
}

fun <B : MultiItemEntity> Context.getDefaultMulListAdapter(layoutResId: Int): BaseMulListAdapter<B> = object : BaseMulListAdapter<B>() {}
