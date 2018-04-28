package uooconline.com.teacher.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.blankj.utilcode.util.TimeUtils
import kotlinx.android.synthetic.main.fragment_one.*
import uooconline.com.education.model.schedule.viewbinder.Type0Binder
import uooconline.com.education.model.schedule.viewbinder.Type1Binder
import uooconline.com.education.model.schedule.viewbinder.Type2Binder
import uooconline.com.education.model.schedule.viewbinder.Type3Binder
import uooconline.com.nucleus.ui.base.BaseFragment
import uooconline.com.nucleus.utils.ext.applyStatusPadding
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.nucleus.widget.treelist.TreeViewAdapter
import uooconline.com.teacher.R
import uooconline.com.teacher.databinding.FragmentOneBinding
import uooconline.com.teacher.presenter.OneFragmentPresenter
import uooconline.com.teacher.request.Bymonth
import uooconline.com.teacher.request.OneList
import uooconline.com.teacher.request.model.bean.Type0
import uooconline.com.teacher.ui.view.IOneFragment
import java.text.DateFormat
import java.text.SimpleDateFormat

class OneFragment:BaseFragment<OneFragmentPresenter,FragmentOneBinding>(),IOneFragment {

    var mAdapter: TreeViewAdapter? = null
    override fun onFirstUserVisible() {
        applyStatusPadding(contain)
        mPresenter.getIdsList()
        mAdapter = TreeViewAdapter(arrayListOf(), arrayListOf(
                Type0Binder(),
                Type1Binder(),
                Type2Binder(),
                Type3Binder()
               ), false)
        mAdapter!!.setOnTreeNodeListener(object :TreeViewAdapter.OnTreeNodeListener{
            override fun onToggle(isExpand: Boolean, holder: RecyclerView.ViewHolder?) {
            }

            override fun onToggleAfter(selectedNode: TreeNode<*>?, isInsert: Boolean, holder: RecyclerView.ViewHolder?) {
                when (holder) {
                    is Type0Binder.ViewHolder -> {
                        holder.toggleRotateArrow(isInsert)
                    }
                }
            }

            override fun onClick(node: TreeNode<*>?, holder: RecyclerView.ViewHolder?): Boolean {
                when (node?.content) {
                    is Type0 ->{
                        mAdapter!!.toggleNode(node, holder)
                    }
                }
             return false
            }

        })
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter

        //刷新控件配置
        mRefreshLayout.setOnRefreshListener {
            onStateViewRetryListener()
        }
        mRefreshLayout.setOnLoadmoreListener {
            var DEFAULT_FORMAT: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = TimeUtils.date2String(TimeUtils.getNowDate(),DEFAULT_FORMAT)
            mPresenter.getOneList(this,date, {
                mAdapter!!.refresh(it)
                mRefreshLayout.finishLoadmore()
            }, {
                mRefreshLayout.finishLoadmore()
            })
        }
        mRefreshLayout.autoRefresh()
    }

    override fun onStateViewRetryListener() {
        super.onStateViewRetryListener()
        mPresenter.getOneList(this,"0", {
            showContent()
            mAdapter!!.refresh(it)
            mRefreshLayout?.finishRefresh(1500)
        }, {
            mRefreshLayout.finishLoadmore()
        })
    }

    override fun setMessage(content: String) {
        if(!TextUtils.isEmpty(content))
            showMessage(content)
    }

    override fun obtainIdList(ids: List<String>?) {
    }

    override fun obtainOneList(content: OneList.Data?) {

    }

    override fun obtainBymonth(content: Bymonth.Data?) {

    }

    override fun getLayoutId(): Int = R.layout.fragment_one
}