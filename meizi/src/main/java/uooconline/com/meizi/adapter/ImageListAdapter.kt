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

package uooconline.com.meizi.adapter

import android.support.v7.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import uooconline.com.meizi.R
import uooconline.com.meizi.model.ImageItem
import uooconline.com.nucleus.ui.adapter.BaseListAdapter

class ImageListAdapter : BaseListAdapter<ImageItem>(R.layout.meizi_image_item) {

    val heights = arrayOf(ScreenUtils.getScreenHeight() / 2, ScreenUtils.getScreenHeight() / 3, ScreenUtils.getScreenHeight() / 4)

    override fun onConvertBindBefore(helper: BindHolder, item: ImageItem) {
        val binding = helper.getBinding()
        val lp = binding.root.layoutParams as? StaggeredGridLayoutManager.LayoutParams
        lp?.height = heights[0]
        lp?.let { binding.root.layoutParams = it }
    }
}