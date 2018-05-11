package uooconline.com.teacher.ui.fragment

import android.graphics.Color
import android.media.Image
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_one.*
import uooconline.com.education.model.schedule.viewbinder.*
import uooconline.com.nucleus.ui.base.BaseFragment
import uooconline.com.nucleus.utils.ext.applyStatusBarBlack
import uooconline.com.nucleus.utils.ext.applyStatusPadding
import uooconline.com.nucleus.widget.treelist.TreeNode
import uooconline.com.nucleus.widget.treelist.TreeViewAdapter
import uooconline.com.teacher.R
import uooconline.com.teacher.databinding.FragmentOneBinding
import uooconline.com.teacher.presenter.AllFragmentPresenter
import uooconline.com.teacher.request.Bymonth
import uooconline.com.teacher.request.model.bean.Type0
import uooconline.com.teacher.ui.view.IAllFragment
import uooconline.com.teacher.ui.view.IChangeStateColor


class AllTypeFragment:BaseFragment<AllFragmentPresenter,FragmentOneBinding>(),IAllFragment, IChangeStateColor {
    override fun onChangeStateColor() {
        activity?.applyStatusBarBlack()
    }
    var mAdapter: TreeViewAdapter? = null
    override fun onFirstUserVisible() {
        applyStatusPadding(contain)
        activity?.applyStatusBarBlack()

        with(mTitlebar ?: return) {
            val iamgeview = ImageView(activity)
            iamgeview.setImageResource(R.mipmap.one_is_all)
            setCenterView(iamgeview)

        }
        mAdapter = TreeViewAdapter(arrayListOf(), arrayListOf(
                TypeAll0Binder(),
                TypeAll1Binder(),
                TypeAll2Binder()
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
            mPresenter.getOneList({
                mAdapter!!.refresh(it)
                mRefreshLayout.finishLoadmore()
            }, {
                mRefreshLayout.finishLoadmore()
            })
        }
        mRefreshLayout.autoRefresh()
    }

    override fun obtainTitle(title: String, subtitle: String) {
        super.obtainTitle(title, subtitle)
        with(mTitlebar ?:return){
            setTitle(title)
        }
    }

    override fun onStateViewRetryListener() {
        super.onStateViewRetryListener()
        mPresenter.getOneList( {
            showContent()
            mAdapter!!.refresh(it)
            mRefreshLayout?.finishRefresh(1500)
        }, {
            mRefreshLayout.finishLoadmore()
        })
    }

    override fun obtainBymonth(content: Bymonth.Data?) {

    }

    override fun getLayoutId(): Int = R.layout.fragment_all
}