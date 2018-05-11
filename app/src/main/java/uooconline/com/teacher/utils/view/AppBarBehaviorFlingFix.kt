package uooconline.com.education.utils.view

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View


/**
 * Created by leiyaogen on 18/1/22.
 */
//解决了AppBarLayout 滑动惯性导致，View拦截取消不及时问题
class AppBarBehaviorFlingFix(context: Context?, attrs: AttributeSet?) : AppBarLayout.Behavior(context, attrs) {
    private var nestView: RecyclerView? = null
    private var swipeDownTrigger: AppBarStateChangeListener? = null

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        nestView = target as? RecyclerView
        if (nestView != null && swipeDownTrigger == null) {
            swipeDownTrigger = object : AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout, state: State,int: Float) {
                    when (state) {
                        State.EXPANDED -> {
                            nestView?.isNestedScrollingEnabled = false//结束惯性：立刻触发 onStopNestedScroll
                            nestView?.postDelayed({
                                nestView?.isNestedScrollingEnabled = true
                            }, 300)//准备下次滑动
                        }
                        else -> {
                        }
                    }
                }
            }
            child.addOnOffsetChangedListener(swipeDownTrigger)
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, abl: AppBarLayout, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)


    }
}