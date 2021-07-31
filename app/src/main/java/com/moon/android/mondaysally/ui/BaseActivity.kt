package com.moon.android.mondaysally.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.utils.GlobalConstant

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(),
    DefaultDialog.WekitDialogClickListener {

    lateinit var lottieDialog: LottieDialog

    @LayoutRes
    abstract fun getLayoutResId(): Int

    protected lateinit var binding: T
        private set


    fun showDialog(title: String) {
        val dig = DefaultDialog(this)
        dig.listener = this
        dig.show(title)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    open fun startNextActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    open fun startActivityWithClear(activity: Class<*>?) {
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    open fun animateViewShake(view: View) {
        view.animate()
            .withEndAction {
                // here you can clear the fields after the shake
            }
            .xBy(-20f)
            .setInterpolator(GlobalConstant.decayingSineWave)
            .setDuration(300)
            .start()
    }

    override fun onOKClicked() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_none)
        lottieDialog = LottieDialog(this)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        initDataBinding()
        initAfterBinding()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_none, R.anim.slide_out_right)
    }


    open fun showLottieDialog() {
        if (isFinishing) {
            return
        }
        lottieDialog.show()
//        lottieDialog.let {
//            if (!lottieDialog.isShowing) {
//            뒤로가기로 끌 수 없게하기
//            lottieDialog.setCancelable(false);
//                lottieDialog.show()
//            }
//        }
    }

    open fun hideLottieDialog() {
        lottieDialog.let {
            if (lottieDialog?.isShowing == true) {
                lottieDialog?.dismiss()
            }
        }
    }

    /**
     * 데이터 바인딩 및 observe 설정.
     * ex)databinding observe..
     */
    protected abstract fun initDataBinding()

    /**
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너 등
     */
    protected abstract fun initAfterBinding()


}