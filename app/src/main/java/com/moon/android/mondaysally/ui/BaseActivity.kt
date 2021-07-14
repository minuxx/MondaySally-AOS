package com.moon.android.mondaysally.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(),
    DefaultDialog.WekitDialogClickListener {

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected lateinit var binding: T
        private set


    fun showDialog(title: String) {
        val dig = DefaultDialog(this)
        dig.listener = this
        dig.show(title)
    }

    override fun onOKClicked() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        initDataBinding()
        initAfterBinding()
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