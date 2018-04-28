package uooconline.com.teacher.presenter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUITabSegment
import com.ricky.mvp_core.base.BasePresenter
import uooconline.com.nucleus.utils.ext.defPolicy
import uooconline.com.nucleus.utils.ext.parse
import uooconline.com.nucleus.utils.impl.IList
import uooconline.com.nucleus.utils.picker.ImagePicker
import uooconline.com.nucleus.widget.InterceptViewpager
import uooconline.com.teacher.R
import uooconline.com.teacher.request.Api
import uooconline.com.teacher.request.ApiCache
import uooconline.com.teacher.ui.fragment.OneFragment
import uooconline.com.teacher.ui.view.IMainActivity

class MainActivityPresenter : BasePresenter<IMainActivity>() {
    override fun onViewCreated(view: IMainActivity, arguments: Bundle?, savedInstanceState: Bundle?) {
    }

    var mBottomTab: QMUITabSegment? = null
    var mPager: InterceptViewpager? = null
    var mSupportFragmentManager: FragmentManager? = null

    fun initTabs(mTabSegment: QMUITabSegment, pager: InterceptViewpager, supportFragmentManager: FragmentManager) {
        ImagePicker.init(view())
        this.mBottomTab = mTabSegment
        this.mPager = pager
        this.mSupportFragmentManager = supportFragmentManager

        tab4()
    }


    private fun tab4() {
        //pager
        val fs = arrayListOf(OneFragment(), OneFragment(), OneFragment())
        with(mPager ?: return) {
            mPager?.adapter = object : FragmentPagerAdapter(mSupportFragmentManager!!) {
                override fun getItem(position: Int): Fragment = fs[position]
                override fun getCount(): Int = fs.size
            }
            offscreenPageLimit = 3
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                }
            })
        }
        //tab
        with(mBottomTab ?: return) {
            val normalColor = QMUIResHelper.getAttrColor(context, R.attr.qmui_config_color_gray_6)
            val selectColor = QMUIResHelper.getAttrColor(context, R.attr.qmui_config_color_blue)
            setDefaultNormalColor(normalColor)
            setDefaultSelectedColor(selectColor)
            reset()
            //ONE
            val schedule = QMUITabSegment.Tab(
                    ContextCompat.getDrawable(context, R.mipmap.one_line),
                    ContextCompat.getDrawable(context, R.mipmap.one_fill),
                    "ONE", false
            )
            //ALL
            val micro = QMUITabSegment.Tab(
                    ContextCompat.getDrawable(context, R.mipmap.all_line),
                    ContextCompat.getDrawable(context, R.mipmap.all_fill),
                   "ALL", false
            )
            //ME
            val found = QMUITabSegment.Tab(
                    ContextCompat.getDrawable(context, R.mipmap.me_line),
                    ContextCompat.getDrawable(context, R.mipmap.me_fill),
                    "ME", false
            )
            addTab(schedule)
            addTab(micro)
            addTab(found)

            setOnTabClickListener { tabIndex ->
                view().onClickDealWithData(tabIndex)
            }
            notifyDataChanged()
        }
        mBottomTab?.setupWithViewPager(mPager, false, false)
    }

}