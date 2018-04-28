package uooconline.com.teacher.ui.view

import com.ricky.mvp_core.base.interfaces.IView

interface IMainActivity : IView {

    //退出登录
    fun logoutSuccess() = Unit

    fun onClickDealWithData(index: Int) = Unit
}