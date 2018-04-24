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

package uooconline.com.nucleus.utils.java

import android.databinding.BindingAdapter
import android.widget.ImageView
import uooconline.com.nucleus.utils.ext.show


@BindingAdapter("android:load_image")
fun loadImage(iv: ImageView, path: Any?) {
    iv.show(path)
}






