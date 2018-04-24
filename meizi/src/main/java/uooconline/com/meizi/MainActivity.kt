package uooconline.com.meizi

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.ImageView
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.blankj.utilcode.util.SizeUtils
import com.github.refresh.RefreshCustomerLayout
import com.github.refresh.util.GridItemDecoration
import uooconline.com.meizi.adapter.ImageListAdapter
import uooconline.com.meizi.model.ImageItem
import uooconline.com.meizi.presenter.MainActivityPresenter
import uooconline.com.meizi.request.Api
import uooconline.com.nucleus.databinding.NucleusCommonListBinding
import uooconline.com.nucleus.resource.Constant
import uooconline.com.nucleus.ui.base.BaseActivity
import uooconline.com.nucleus.utils.ext.*
import uooconline.com.nucleus.utils.impl.IList

class MainActivity : BaseActivity<MainActivityPresenter, NucleusCommonListBinding>(), IList {
    override fun setData(beanList: List<*>, loadMore: Boolean) {
        mBinding.mRefreshLayout.setData(beanList, loadMore)
    }

    override fun setMessage(error: Any, content: String) {
        mBinding.mRefreshLayout.setMessage(error, content)
    }

    override fun getLayoutId(): Int = R.layout.nucleus_common_list


    var mAdapter = ImageListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()
        applyStatusPadding(mBinding.mRefreshLayout)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val iv = view as ImageView
            if (iv.drawable !is ColorDrawable) {
                iv.zoom(((adapter.getItem(position) as ImageItem)).url.toString())
            }
        }
        mBinding.mRefreshLayout
                .setLayoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
                .setItemDecoration(GridItemDecoration(2, SizeUtils.dp2px(5f), false))
                .setLoadSize(Api.pageSize)
                .setPageStartOffset(Api.startOffset)
                .setViewType(RefreshCustomerLayout.Refresh_LoadMore)
                .setViewStateListener(
                        { showEmpty() },
                        { showContent() },
                        { showLoading() },
                        { showMessage(it) },
                        { error, content -> showMessageFromNet(error, content) })
                .setRefreshListener(
                        { mPresenter.getImageList(Api.startOffset, false) },
                        { _, targetPage -> mPresenter.getImageList(targetPage, true) })
                .setAdapter(mAdapter)

        mBinding.mRefreshLayout.startRequest()
    }

    override fun onStateViewRetryListener() {
        mBinding.mRefreshLayout.startRequest()
    }

    override fun onDestroy() {
        super.onDestroy()
        CC.sendCCResult(intent.getStringExtra("callId"), CCResult.success("fuck", "you"))
    }
}