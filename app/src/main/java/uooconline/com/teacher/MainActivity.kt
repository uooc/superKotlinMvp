package uooconline.com.teacher

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.github.refresh.RefreshCustomerLayout
import uooconline.com.nucleus.databinding.NucleusCommonListBinding
import uooconline.com.nucleus.resource.Constant
import uooconline.com.nucleus.ui.adapter.getDefaultListAdapter
import uooconline.com.nucleus.ui.base.BaseActivity
import uooconline.com.nucleus.utils.ext.applyStatusBarBlack
import uooconline.com.nucleus.utils.ext.applyStatusPadding
import uooconline.com.nucleus.utils.ext.setRefreshListener
import uooconline.com.nucleus.utils.ext.setViewStateListener
import uooconline.com.nucleus.utils.impl.IList
import uooconline.com.nucleus.utils.router.routerActivity
import uooconline.com.nucleus.utils.router.routerActivityForResult
import uooconline.com.teacher.presenter.MainActivityPresenter
import uooconline.com.teacher.request.Api
import uooconline.com.teacher.request.model.TextItem

class MainActivity : BaseActivity<MainActivityPresenter, NucleusCommonListBinding>(), IList {
    override fun setData(beanList: List<*>, loadMore: Boolean) {
        mBinding.mRefreshLayout.setData(beanList, loadMore)
    }

    override fun setMessage(error: Any, content: String) {
        mBinding.mRefreshLayout.setMessage(error, content)
    }

    override fun getLayoutId(): Int = R.layout.nucleus_common_list


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()
        applyStatusPadding(mBinding.mRefreshLayout)
        val mAdapter = getDefaultListAdapter<TextItem>(R.layout.item_tv)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            routerActivityForResult(Constant.Meizi.Meizi, Constant.Meizi.MainActivity, {
                println()
            }, Pair("url", "http://www.baidu.com"), Pair("title", "百度"), Pair("order", 1153))
        }
        mBinding.mRefreshLayout
                .setLayoutManager(LinearLayoutManager(this))
                .setItemDecoration(DividerItemDecoration(mBinding.mRefreshLayout.context, LinearLayout.VERTICAL))
                .setLoadSize(Api.pageSize)
                .setPageStartOffset(1)
                .setViewType(RefreshCustomerLayout.Refresh)
                .setViewStateListener(
                        { showEmpty() },
                        { showContent() },
                        { showLoading() },
                        { showMessage(it) },
                        { error, content -> showMessageFromNet(error, content) })
                .setRefreshListener(
                        { mPresenter.getDatas(this) },
                        { _, targetPage -> })
                .setAdapter(mAdapter)

        mBinding.mRefreshLayout.startRequest()
    }

}
