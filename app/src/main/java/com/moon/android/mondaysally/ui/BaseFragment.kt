package com.moon.android.mondaysally.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.moon.android.mondaysally.R

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

    open fun startNextActivity(activity: Class<*>?) {
        activity?.let{
            val iT = Intent(context, activity)
            startActivity(iT)
        }
    }

    fun setCircleImageByGlide(iv: ImageView, url: String) {
        Glide.with(this)
            .load(url).placeholder(R.drawable.bg_round_white_gray)
            .error(R.drawable.illust_sally_profile_blank)
            .centerCrop()
            .circleCrop()
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv)
    }

    fun setImage(iv: ImageView, url: String) {
        Glide.with(this)
            .load(url)
            .thumbnail(0.2f)
            .override(700, 700)
            .error(R.drawable.illust_sally_blank_1_1)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv)
    }

    fun showToast(message: String) {
        activity?.let {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getErrorState(loadState: CombinedLoadStates): LoadState.Error? {
        return when {
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }
    }
}