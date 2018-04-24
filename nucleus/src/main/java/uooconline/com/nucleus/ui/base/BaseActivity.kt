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

package uooconline.com.nucleus.ui.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import com.kennyc.view.MultiStateView
import com.qmuiteam.qmui.widget.QMUITopBar
import com.ricky.mvp_core.base.BaseBindingActivity
import com.ricky.mvp_core.base.BasePresenter
import com.tbruyelle.rxpermissions2.RxPermissions
import com.umeng.analytics.MobclickAgent
import org.jetbrains.anko.findOptional
import uooconline.com.nucleus.BuildConfig
import uooconline.com.nucleus.R
import uooconline.com.nucleus.utils.ext.toast
import uooconline.com.nucleus.utils.impl.IEventBus
import uooconline.com.nucleus.utils.impl.IStateView


//stateView and eventBus
abstract class BaseActivity<T : BasePresenter<*>, B : ViewDataBinding> : BaseBindingActivity<T, B>(), IStateView, IEventBus {

    val isDevEnvironment:Boolean by lazy { BuildConfig.DEBUG }
    val mRxPermissions: RxPermissions by lazy { RxPermissions(this) }
    val mTitlebar: QMUITopBar? by lazy { findOptional<QMUITopBar>(R.id.topbar) }

    override fun getStateView(): MultiStateView? {
        return super.getStateView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.registerEventBus(this)
        super.stateViewSetup(this)
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        super.unregisterEventBus(this)
    }

    override fun showLoading() {
        super.showLoading()
        super.stateViewLoading()
    }

    override fun showMessageFromNet(error: Any, content: String) {
        super.showMessageFromNet(error, content)
        super.stateViewError(error, content)
    }

    override fun showEmpty() {
        super.showEmpty()
        super.stateViewEmpty()
    }

    fun showEmpty(image: Int, content: String) {
        super.showEmpty()
        super.stateViewEmpty(image, content)
    }

    fun showEmpty(resLayoutId: Int, clickViewId: Int, clickViewCallback: () -> Unit): View? {
        super.showEmpty()
        return super.stateViewEmpty(resLayoutId, clickViewId, clickViewCallback)
    }

    override fun showContent() {
        super.showContent()
        super.stateViewContent()
    }

    override fun showMessage(content: String) {
        super.showMessage(content)
        toast(content)
    }

    override fun hideLoading() {
        super.hideLoading()
        showContent()
    }
}
