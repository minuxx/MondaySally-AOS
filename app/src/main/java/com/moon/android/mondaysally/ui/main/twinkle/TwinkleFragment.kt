package com.moon.android.mondaysally.ui.main.twinkle

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Twinkle
import com.moon.android.mondaysally.databinding.FragmentTwinkleBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.MainActivity
import com.moon.android.mondaysally.ui.main.twinkle.paging.MyTwinkleAdapter
import com.moon.android.mondaysally.ui.main.twinkle.paging.TwinkleAdapter
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail.TwinkleDetailActivity
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_post.TwinklePostActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwinkleFragment() :
    BaseFragment<FragmentTwinkleBinding>() {

    private val twinkleViewModel: TwinkleViewModel by viewModel()
    private lateinit var twinkleAdapter: TwinkleAdapter
    private lateinit var myTwinkleAdapter: MyTwinkleAdapter

    override fun getLayoutResId() = R.layout.fragment_twinkle

    private val twinkleDetailActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val position = result.data?.getIntExtra("position", -1)
                val isHearted = result.data?.getStringExtra("isHearted")
                val likenum = result.data?.getIntExtra("likenum", -1)
                if (position != null && position > -1) {
                    twinkleAdapter.snapshot()[position]?.let {
                        if (isHearted != null) {
                            it.isHearted = isHearted
                        }
                        if (likenum != null) {
                            it.likenum = likenum
                        }
                    }
                }
            }
        }

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = twinkleViewModel

        twinkleViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
//                (activity as MainActivity).showLottieDialog(activity as MainActivity)
            } else {
//                (activity as MainActivity).hideLottieDialog()
                binding.fragmentTwinkleSwipeRefresh.isRefreshing = false;
            }
        })
        binding.fragmentTwinkleSwipeRefresh.setColorSchemeResources(
            R.color.pinkish_orange
        )
        binding.fragmentTwinkleSwipeRefresh.setOnRefreshListener {
            twinkleAdapter.refresh()
            myTwinkleAdapter.refresh()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initAfterBinding() {
        twinkleAdapter = TwinkleAdapter()
        myTwinkleAdapter = MyTwinkleAdapter()

        binding.fragmentTwinkleRvTimeline.adapter = twinkleAdapter
        binding.fragmentTwinkleRvMyTwinkle.adapter = myTwinkleAdapter

        myTwinkleAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    twinkleViewModel.isLoading.value = true
                }
                is LoadState.NotLoading -> {
                    twinkleViewModel.isLoading.value = false
                    if (myTwinkleAdapter.itemCount == 0) {
                        binding.fragmentShopTvAllGift.visibility = GONE
                        binding.fragmentTwinkleLine.visibility = GONE
                    }else{
                        binding.fragmentShopTvAllGift.visibility = VISIBLE
                        binding.fragmentTwinkleLine.visibility = VISIBLE
                    }
                }
                is LoadState.Error -> {
                    twinkleViewModel.isLoading.value = false
                    binding.fragmentShopTvAllGift.visibility = GONE
                    binding.fragmentTwinkleLine.visibility = GONE
                    getErrorState(loadState)?.let {
                        showToast(getString(R.string.default_fail))
                    }
                }
            }
        }

        twinkleAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    twinkleViewModel.isLoading.value = true
                }
                is LoadState.NotLoading -> {
                    Log.d("페이징", "" + twinkleAdapter.itemCount)
                    twinkleViewModel.isLoading.value = false
                }
                is LoadState.Error -> {
                    twinkleViewModel.isLoading.value = false
                    getErrorState(loadState)?.let {
                        showToast(getString(R.string.default_fail))
                    }
                }
            }
        }

        myTwinkleAdapter.setOnItemClickListener { myTwinkle ->
            if(myTwinkle.isProved != "Y") {
                twinkleViewModel.twinkleIndex.value = myTwinkle.idx
                activity?.let {
                    val intent = Intent(context, TwinklePostActivity::class.java)
                    intent.putExtra("idx", myTwinkle.idx)
                    intent.putExtra("name", myTwinkle.name)
                    intent.putExtra("usedClover", myTwinkle.usedClover)
                    startActivity(intent)
                }
            }
        }

        twinkleAdapter.setOnItemClickListener { twinkle, position ->
            twinkleViewModel.twinkleIndex.value = twinkle.idx
            activity?.let {
                val intent = Intent(context, TwinkleDetailActivity::class.java)
                intent.putExtra("idx", twinkle.idx)
                intent.putExtra("position", position)
                twinkleDetailActivityLauncher.launch(intent)
            }
        }

        twinkleAdapter.setOnHeartClickListener { twinkle, heartIv, likeTv, likeNum ->
            heartImageChange(heartIv, likeTv, twinkle, likeNum)
            animateHeart(heartIv)
            twinkleViewModel.postLike(twinkle.idx)
        }

        loadData()
    }


    private fun heartImageChange(
        imageView: ImageView,
        textView: TextView,
        twinkle: Twinkle,
        likeNum: Int
    ) {
        if (twinkle.isHearted == "Y") {
            imageView.setImageResource(R.drawable.ic_like_off_gray)
            twinkle.isHearted = "N"
            twinkle.likenum--
        } else {
            (activity as MainActivity).vibrate()
            imageView.setImageResource(R.drawable.ic_like_on_orange)
            twinkle.isHearted = "Y"
            twinkle.likenum++
        }
        textView.text = "좋아요 ${twinkle.likenum}개"
    }

    private  fun animateHeart(view: ImageView) {
        view.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).withEndAction {
            view.scaleX = 1f
            view.scaleY = 1f
        }.start()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            twinkleViewModel.twinkleFlow.collectLatest { pagingData ->
                twinkleAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            twinkleViewModel.myTwinkleFlow.collectLatest { pagingData ->
                myTwinkleAdapter.submitData(pagingData)
            }
        }
    }
}