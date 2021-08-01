package com.moon.android.mondaysally.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.utils.DataBindingUtils.setImageCommon

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    @LayoutRes

    abstract fun getLayoutResId(): Int

    protected lateinit var binding: T
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<T>(inflater, getLayoutResId(), container, false)
            .apply { binding = this }.root
    }


    override fun onStart() {
        super.onStart()
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
     * 클릭 리스너 등
     */
    protected abstract fun initAfterBinding()

    fun setCircleImageByGlide(iv: ImageView, url: String){
        Glide.with(this)
            .load(url).placeholder(R.drawable.bg_round_white_gray)
            .error(R.drawable.bg_round_white_gray)
            .centerCrop()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv)
    }
}