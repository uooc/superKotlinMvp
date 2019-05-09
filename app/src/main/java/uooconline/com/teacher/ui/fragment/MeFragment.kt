package uooconline.com.teacher.ui.fragment

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.layout_me_head.*
import uooconline.com.education.utils.view.AppBarStateChangeListener
import uooconline.com.nucleus.resource.Constant
import uooconline.com.nucleus.ui.base.BaseFragment
import uooconline.com.nucleus.utils.ext.applyStatusBarBlack
import uooconline.com.nucleus.utils.ext.applyStatusBarWhite
import uooconline.com.nucleus.utils.ext.applyStatusMargin
import uooconline.com.nucleus.utils.router.routerActivity
import uooconline.com.teacher.R
import uooconline.com.teacher.databinding.FragmentOneBinding
import uooconline.com.teacher.presenter.MeFragmentPresenter
import uooconline.com.teacher.ui.view.IChangeStateColor
import uooconline.com.teacher.ui.view.IMeFragment

class MeFragment:BaseFragment<MeFragmentPresenter,FragmentOneBinding>(), IMeFragment , IChangeStateColor {

    override fun onChangeStateColor() {
        when (appBarListener?.mCurrentState) {
            AppBarStateChangeListener.State.IDLE,
            AppBarStateChangeListener.State.COLLAPSED -> {
                activity?.applyStatusBarBlack()
            }
            AppBarStateChangeListener.State.EXPANDED -> {
                activity?.applyStatusBarWhite()
            }
        }
    }

    var appBarListener: AppBarStateChangeListener? = null

    override fun onFirstUserVisible() {
        activity?.applyStatusBarWhite()
        applyStatusMargin(mToolbar)

        with(mTitlebar ?: return) {
            setBackgroundColor(Color.TRANSPARENT)
        }
        appBarListener = object : AppBarStateChangeListener() {

            override fun onStateChanged(appBarLayout: AppBarLayout, state: State,i: Float) {
                when (state) {
                    State.COLLAPSED -> {
                        with(mTitlebar ?: return) {
                            setTitle("Gen哥哥")
                            activity?.applyStatusBarBlack()
                            setBackgroundColor(Color.WHITE)
                        }
                    }
                    State.EXPANDED -> {
                        with(mTitlebar ?: return) {
                            setTitle("")
                            activity?.applyStatusBarWhite()
                            setBackgroundColor(Color.TRANSPARENT)
                        }
                    }
                    else -> {
                        with(mTitlebar ?: return) {
                            setTitle("")
                            activity?.applyStatusBarWhite()
                            if(i != 0.0f){
                                alpha = i*1f
                            }else{
                                setBackgroundColor(Color.WHITE)
                            }
                        }
                    }
                }
            }
        }
        mAppbarLayout.addOnOffsetChangedListener(appBarListener)

        iv_head.setOnClickListener {
            activity?.routerActivity(Constant.Meizi.Meizi,Constant.Meizi.MainActivity)
        }

    }


    override fun getLayoutId(): Int = R.layout.fragment_me
}