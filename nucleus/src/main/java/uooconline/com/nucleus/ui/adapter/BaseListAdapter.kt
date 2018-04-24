/*
 * Copyright (C) 2017 Ricky.yao https://github.com/vihuela
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */

package uooconline.com.nucleus.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import uooconline.com.nucleus.R
import uooconline.com.nucleus.BR

abstract class BaseListAdapter<B>(layoutResId: Int, data: List<B> = arrayListOf()) : BaseQuickAdapter<B, BaseListAdapter.BindHolder>(layoutResId, data) {

    final override fun convert(helper: BindHolder, item: B) {

        onConvertBindBefore(helper, item)

        //默认数据绑定的变量的id是【item】
        val binding = helper.getBinding()
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()

        onConvertBindAfter(helper, item)
    }

    open fun onConvertBindBefore(helper: BindHolder, item: B) {}
    open fun onConvertBindAfter(helper: BindHolder, item: B) {}

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

fun <B> Context.getDefaultListAdapter(layoutResId: Int): BaseListAdapter<B> = object : BaseListAdapter<B>(layoutResId) {}