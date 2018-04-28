package uooconline.com.teacher

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import uooconline.com.nucleus.ui.base.BaseActivity
import uooconline.com.nucleus.utils.eventbus.Event
import uooconline.com.nucleus.utils.ext.applyStatusBarBlack
import uooconline.com.teacher.databinding.ActivityMainBinding
import uooconline.com.teacher.presenter.MainActivityPresenter
import uooconline.com.teacher.ui.view.IMainActivity

class MainActivity : BaseActivity<MainActivityPresenter, ActivityMainBinding>(), IMainActivity {

    override fun getLayoutId(): Int = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStatusBarBlack()
        mPresenter.initTabs(tabs, pager, supportFragmentManager)
//        val mAdapter = getDefaultListAdapter<TextItem>(R.layout.item_tv)
//        mAdapter.setOnItemClickListener { adapter, view, position ->
//            routerActivityForResult(Constant.Meizi.Meizi, Constant.Meizi.MainActivity, {
//                println()
//            }, Pair("url", "http://www.baidu.com"), Pair("title", "百度"), Pair("order", 1153))
//        }
//        mBinding.mRefreshLayout
//                .setLayoutManager(LinearLayoutManager(this))
//                .setItemDecoration(DividerItemDecoration(mBinding.mRefreshLayout.context, LinearLayout.VERTICAL))
//                .setLoadSize(Api.pageSize)
//                .setPageStartOffset(1)
//                .setViewType(RefreshCustomerLayout.Refresh)
//                .setViewStateListener(
//                        { showEmpty() },
//                        { showContent() },
//                        { showLoading() },
//                        { showMessage(it) },
//                        { error, content -> showMessageFromNet(error, content) })
//                .setRefreshListener(
//                        { mPresenter.getDatas(this) },
//                        { _, targetPage -> })
//                .setAdapter(mAdapter)
//
//        mBinding.mRefreshLayout.startRequest()
    }

    override fun isRegisterEventBus(): Boolean = true
    override fun <T> onEvent(event: Event<T>?) {
        super.onEvent(event)
    }

}
