package com.moon.android.mondaysally.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.moon.android.mondaysally.ui.main.MainActivity

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

    open fun startNextActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    open fun startActivityWithClear(activity: Class<*>?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
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