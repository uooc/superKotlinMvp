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

package uooconline.com.nucleus.utils.impl

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.github.refresh.RefreshCustomerLayout
import com.kennyc.view.MultiStateView
import org.jetbrains.anko.findOptional
import uooconline.com.nucleus.R

//apply for MultiStateView
//step 1: xml文件中标识 android:contentDescription="setup_stateView"
//step 2：在activity或fragment 使用布局之前调用stateViewSetup()方法完成布局调整
//step 3：外部实现此接口，覆写通过getStateView()方法，调用getStateView(context: T)
interface IStateView {

    //初始化使用--------------------------------------------------------------------------------------
    fun getStateView(): MultiStateView? = null

    fun <T> getStateView(context: T): MultiStateView? {
        when (context) {
            is Fragment -> {
                return context.view?.findOptional<MultiStateView>(R.id.multiStateView)
            }
            is Activity -> {
                return context.findOptional<MultiStateView>(R.id.multiStateView)
            }
            else -> throw IllegalArgumentException("context must be Fragment Or Activity")
        }
    }

    /**
     * 此方法一定要在fragment与activity 的用户操作布局之前调用，完成布局调整
     * 通常fragment中在onCreateView()调用，activity中onCreate()调用
     */
    fun <T> stateViewSetup(source: T) {

        if (!isRegisterStateView()) return

        val root: View = when (source) {
            is View -> source
            is Activity -> source.findViewById(android.R.id.content)
            else -> throw IllegalArgumentException("source must be View Or Activity")
        }

        val targetRoot: ViewGroup = root as? ViewGroup ?: return
        val TAG: String = "setup_stateView"
        val outputViews = ArrayList<View>()
        targetRoot.findViewsWithText(outputViews, TAG, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
        if (outputViews.size == 1) {
            val targetContent = when (outputViews[0]) {
                is RefreshCustomerLayout -> outputViews[0].findViewById(R.id.mRecycleView)
                else -> outputViews[0]
            }
            val targetContentLp = targetContent.layoutParams
            val targetParent = targetContent.parent as? ViewGroup ?: return
            val targetIndex: Int = targetParent.indexOfChild(targetContent)

            targetParent.apply {
                removeViewAt(targetIndex)
                val stateView = MultiStateView(targetRoot.context).apply {
                    id = R.id.multiStateView
                    setViewForState(targetContent, MultiStateView.VIEW_STATE_CONTENT)
                    setViewForState(R.layout.nucleus_error_view, MultiStateView.VIEW_STATE_ERROR)
                    setViewForState(R.layout.nucleus_empty_view, MultiStateView.VIEW_STATE_EMPTY)
                    setViewForState(R.layout.nucleus_loading_view, MultiStateView.VIEW_STATE_LOADING)
                    setAnimateLayoutChanges(true)
                    viewState = MultiStateView.VIEW_STATE_LOADING
                }
                addView(stateView, targetIndex, targetContentLp)
            }
        }
    }

    fun isRegisterStateView(): Boolean = true

    //提供运行时方法--------------------------------------------------------------------------------------
    fun onStateViewRetryListener() = Unit

    fun stateViewLoading() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_LOADING
    }

    fun stateViewError(error: Any, content: String) {
        getStateView()?.apply {
            viewState = MultiStateView.VIEW_STATE_ERROR
            val errorView = getView(MultiStateView.VIEW_STATE_ERROR)?.findOptional<TextView>(R.id.tv)
            val retryButton = getView(MultiStateView.VIEW_STATE_ERROR)?.findOptional<Button>(R.id.retry)
            val netErrorIv = getView(MultiStateView.VIEW_STATE_ERROR)?.findOptional<View>(R.id.ic_net_error)
            errorView?.text = content
            retryButton?.setOnClickListener { onStateViewRetryListener() }
            when (error) {
                github.library.utils.Error.NetWork -> netErrorIv?.visibility = View.VISIBLE
                else -> netErrorIv?.visibility = View.GONE
            }
        }
    }

    //默认空布局
    fun stateViewEmpty() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    //默认空布局修改图片和文字
    fun stateViewEmpty(image: Int, content: String) {
        getStateView()?.apply {
            viewState = MultiStateView.VIEW_STATE_EMPTY
            val emptyTxt = getView(MultiStateView.VIEW_STATE_EMPTY)?.findOptional<TextView>(R.id.tv)
            val emptyImg = getView(MultiStateView.VIEW_STATE_EMPTY)?.findOptional<ImageView>(R.id.iv_empty)
            emptyTxt?.text = content
            emptyImg?.setImageResource(image)
        }
    }

    //自定义空布局
    fun stateViewEmpty(resLayoutId: Int, clickViewId: Int, clickViewCallback: () -> Unit): View? {
        getStateView()?.apply {
            setViewForState(resLayoutId, MultiStateView.VIEW_STATE_EMPTY, true)
            val targetClickView = getView(MultiStateView.VIEW_STATE_EMPTY)?.findOptional<View>(clickViewId)
            targetClickView?.setOnClickListener {
                clickViewCallback.invoke()
            }
        }
        return getStateView()?.getView(MultiStateView.VIEW_STATE_EMPTY)
    }

    fun stateViewContent() {
        getStateView()?.viewState = MultiStateView.VIEW_STATE_CONTENT
    }


}
