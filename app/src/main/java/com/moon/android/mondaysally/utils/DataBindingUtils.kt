package com.moon.android.mondaysally.utils

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.GiftHistory
import com.moon.android.mondaysally.data.entities.Member
import com.moon.android.mondaysally.data.entities.TwinkleComment
import com.moon.android.mondaysally.ui.main.home.GiftHistoryAdapter
import com.moon.android.mondaysally.ui.main.home.MemberListAdapter
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail.CommentAdapter

object DataBindingUtils {

    @BindingAdapter("bind_common_image")
    @JvmStatic
    fun ImageView.setImageCommon(url: String?) {
        url.let {
            Glide.with(this)
                .load(url)
                .error(R.drawable.illust_sally_blank_1_1)
                .centerCrop()
                .thumbnail(0.3f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }

    @BindingAdapter("bind_common_image_square")
    @JvmStatic
    fun ImageView.setImageCommonSquare(url: String?) {
        url.let {
            Glide.with(this)
                .load(url).placeholder(R.drawable.illust_sally_blank_1_1)
                .error(R.drawable.illust_sally_blank_1_1)
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }

    @BindingAdapter("bind_common_image_circle")
    @JvmStatic
    fun ImageView.setImageCommonCircle(url: String?) {
        url.let {
            Glide.with(this)
                .load(url)
                .override(200, 200)
                .error(
                    Glide.with(this)
                        .load(R.drawable.illust_sally_blank_1_1).circleCrop()
                )
                .centerCrop()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.2f)
                .into(this)
        }
    }

    @BindingAdapter("bind_large_image")
    @JvmStatic
    fun ImageView.setLargeImageCommon(url: String?) {
        url.let {
            Glide.with(this)
                .load(url)
                .override(700, 700)
                .error(R.drawable.illust_sally_blank_1_1)
                .centerCrop()
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }

    @BindingAdapter("bind_common_text")
    @JvmStatic
    fun bindCommonText(textView: TextView, text: String) {
        textView.text = text
    }

    @BindingAdapter("bind_gift_history")
    @JvmStatic
    fun bindGiftHistoryList(recyclerView: RecyclerView, items: MutableList<GiftHistory>) {
        if (recyclerView.adapter == null) {
            val lm = LinearLayoutManager(recyclerView.context)
            lm.orientation = LinearLayoutManager.HORIZONTAL
            val adapter = GiftHistoryAdapter()
            recyclerView.layoutManager = lm
            recyclerView.adapter = adapter
        }
        (recyclerView.adapter as GiftHistoryAdapter).items = items
        recyclerView.adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("bind_gift_history_status", "bind_gift_history_status2")
    @JvmStatic
    fun bindText(textView: TextView, isAccepted: String?, isProved: String?) {
        isAccepted.let { textView.text = textView.context.getString(R.string.gift_history_hold) }
    }

    @BindingAdapter("bind_gift_history_image")
    @JvmStatic
    fun ImageView.setUrlImage(url: String?) {
        url.let {
            Glide.with(this)
                .load(url).placeholder(R.drawable.illust_sally_blank_1_1)
                .error(R.drawable.illust_sally_blank_1_1)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .into(this)
        }
    }

    @BindingAdapter("bind_member")
    @JvmStatic
    fun bindMemberList(recyclerView: RecyclerView, items: MutableList<Member>) {
        if (recyclerView.adapter == null) {
            val adapter = GiftHistoryAdapter()
            recyclerView.adapter = adapter
        }
        (recyclerView.adapter as MemberListAdapter).items = items
        recyclerView.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("bind_member_nickname", "bind_member_department")
    @JvmStatic
    fun bindMemberName(textView: TextView, nickname: String?, department: String?) {
        textView.text = "$nickname / $department"
    }

    @BindingAdapter("bind_my_twinkle_bg")
    @JvmStatic
    fun bindTwinkleBg(imageView: ImageView, isProved: String) {
        if (isProved.equals("Y"))
            imageView.visibility = GONE
        else
            imageView.visibility = VISIBLE
    }

    @BindingAdapter("bind_twinkle_iv_heart")
    @JvmStatic
    fun bindTwinkleHeart(imageView: ImageView, isHearted: String) {
        if (isHearted.equals("Y"))
            imageView.setImageResource(R.drawable.ic_like_on_orange)
        else
            imageView.setImageResource(R.drawable.ic_like_off_gray)

    }

    @BindingAdapter("bind_twinkle_tv_comment")
    @JvmStatic
    fun bindTwinkleComment(textView: TextView, commentnum: Int) {
        val text = "댓글 ${commentnum}개"
        textView.text = text
    }

    @BindingAdapter("bind_twinkle_tv_like")
    @JvmStatic
    fun bindTwinkleLike(textView: TextView, likenum: Int) {
        val text = "좋아요 ${likenum}개"
        textView.text = text
    }

    @BindingAdapter("bind_gift_shop_tv_count")
    @JvmStatic
    fun bindGiftShopCount(textView: TextView, count: Int) {
        val text = "총 ${count}건"
        textView.text = text
    }

    @BindingAdapter("bind_comment")
    @JvmStatic
    fun bindCommentList(recyclerView: RecyclerView, items: MutableList<TwinkleComment>) {
        if (recyclerView.adapter == null) {
            val adapter = CommentAdapter()
            recyclerView.adapter = adapter
        }
        (recyclerView.adapter as CommentAdapter).items = items
        recyclerView.adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("bind_comment_name", "bind_comment_content")
    @JvmStatic
    fun bindCommentContent(textView: TextView, name: String?, content: String?) {
        val spannable = SpannableString("$name  $content")
        if (name != null) {
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                name.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textView.text = spannable
    }
}