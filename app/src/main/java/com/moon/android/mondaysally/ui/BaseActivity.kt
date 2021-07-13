package com.moon.android.mondaysally.ui

import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity(), View.OnClickListener, DefaultDialog.WekitDialogClickListener{
    override fun onClick(v: View?) {

    }

    fun showDialog(title: String){
        val dig = DefaultDialog(this)
        dig.listener = this
        dig.show(title)
    }

    override fun onOKClicked() {

    }

    /**
     * 데이터 바인딩 및 observe 설정.
     * ex)databinding observe..
     */
    protected abstract fun initDataBinding()

    /**
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */
    protected abstract fun initAfterBinding()



}