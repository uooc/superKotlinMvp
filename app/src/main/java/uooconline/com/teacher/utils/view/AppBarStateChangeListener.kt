package uooconline.com.education.utils.view

import android.support.design.widget.AppBarLayout


//因onOffsetChanged回调会不断回调，故封装此类
abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    var mCurrentState = State.IDLE

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    final override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
//        LogUtils.d(i.toString())
        when {
            i == 0 -> {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED)
                }
                mCurrentState = State.EXPANDED
            }
            Math.abs(i) >= appBarLayout.totalScrollRange -> {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED)
                }
                mCurrentState = State.COLLAPSED
            }
            else -> {
//                if (mCurrentState != State.IDLE) {
                val alphaTranslate :Float= (Math.abs(i).toFloat())/Math.abs(appBarLayout.totalScrollRange)
                onStateChanged(appBarLayout, State.IDLE,alphaTranslate)
                onStateChanged(appBarLayout, State.IDLE)
//                }
                mCurrentState = State.IDLE
            }
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State,i: Float=0f)
}