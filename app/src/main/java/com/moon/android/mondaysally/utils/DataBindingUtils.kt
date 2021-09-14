package com.moon.android.mondaysally.utils

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.GiftHistory
import com.moon.android.mondaysally.data.entities.Member
import com.moon.android.mondaysally.data.entities.TwinkleComment
import com.moon.android.mondaysally.data.entities.TwinkleRanking
import com.moon.android.mondaysally.ui.main.clover.CloverRankingAdapter
import com.moon.android.mondaysally.ui.main.home.GiftHistoryAdapter
import com.moon.android.mondaysally.ui.main.home.MemberListAdapter
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail.CommentAdapter
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DataBindingUtils {

    @BindingAdapter("bind_original_image")
    @JvmStatic
    fun ImageView.setImageCommonSquare(url: String?) {
        url.let {
            Glide.with(this)
                .load(url).placeholder(R.drawable.illust_sally_blank_1_1)
                .error(R.drawable.illust_sally_blank_1_1)
                .thumbnail(0.2f)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }

    @BindingAdapter("bind_profile_image_circle")
    @JvmStatic
    fun ImageView.bindProfileImageCircle(url: String?) {
        url.let {
            Glide.with(this)
                .load(url)
                .override(200, 200)
                .error(R.drawable.illust_sally_blank_circle)
                .centerCrop()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.2f)
                .into(this)
        }
    }

    @BindingAdapter("bind_profile_edit_image_circle")
    @JvmStatic
    fun ImageView.bindProfileEditImage(url: String?) {
        url.let {
            Glide.with(this)
                .load(url)
                .override(500, 500)
                .error(R.drawable.ic_photo_mid)
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
        isProved.let {
            if (it == "Y") {
                //블러처리(트윙클 작성 완료)
                if (isAccepted == null) {
                    //승인대기
                    textView.text = textView.context.getString(R.string.gift_history_hold)
                } else if (isAccepted == "Y") {
                    //승인완료
                    textView.text = textView.context.getString(R.string.gift_history_confirm)
                } else {
                    //승인거부
                    textView.text = textView.context.getString(R.string.gift_history_rejected)
                }
            } else {
                //승인대기 (트윙클 작성 필요)
                textView.text = textView.context.getString(R.string.gift_history_hold)
            }
        }
    }

    @BindingAdapter("bind_gift_history_blur")
    @JvmStatic
    fun bindGiftHistoryBlur(imageView: ImageView, isProved: String) {
        if (isProved == "Y")
            imageView.visibility = VISIBLE
        else
            imageView.visibility = INVISIBLE
    }

    @BindingAdapter("bind_view_blur")
    @JvmStatic
    fun bindViewBlur(view: View, isProved: String) {
        if (isProved == "Y")
            view.visibility = VISIBLE
        else
            view.visibility = INVISIBLE
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
        if (isProved == "Y")
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

    @BindingAdapter("bind_gift_history_tv_count")
    @JvmStatic
    fun bindGiftHistoryCount(textView: TextView, count: Int) {
        val text = "총 ${count}건의 기프트"
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

    @BindingAdapter("bind_twinkle_post_photo")
    @JvmStatic
    fun ImageView.setTwinklePostPhoto(uri: Uri?) {
        uri.let {
            Glide.with(this)
                .load(uri)
                .override(700, 700)
                .error(R.drawable.button_photo_add)
                .centerCrop()
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }

    @BindingAdapter("bind_gift_history_twinkle_status", "bind_gift_history_twinkle_status2")
    @JvmStatic
    fun bindGiftHistoryTwinkleStatus(textView: TextView, isAccepted: String?, isProved: String?) {
        isProved.let {
            if (it == "Y") {
                //블러처리(트윙클 작성 완료)
                if (isAccepted == null) {
                    //승인대기
                    textView.text = textView.context.getString(R.string.gift_history_twinkle_hold)
                } else if (isAccepted == "Y") {
                    //승인완료
                    textView.text =
                        textView.context.getString(R.string.gift_history_twinkle_confirm)
                } else {
                    //승인거부
                    textView.text =
                        textView.context.getString(R.string.gift_history_twinkle_rejected)
                }
            } else {
                //승인대기 (트윙클 작성 필요)
                textView.text = textView.context.getString(R.string.gift_history_twinkle_hold)
            }
        }
    }

    @BindingAdapter("bind_twinkle_ranking_tv_rank")
    @JvmStatic
    fun bindTwinkleRankingTvRank(textView: TextView, ranking: Int) {
        val text = "${ranking}등"
        textView.text = text
    }

    @BindingAdapter("bind_twinkle_ranking_tv_clover")
    @JvmStatic
    fun bindTwinkleRankingTvClover(textView: TextView, currentClover: Int) {
        val text = "${DecimalFormat("#,###").format(currentClover)} clover"
        textView.text = text
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    @BindingAdapter("bind_twinkle_ranking_tv_date")
    @JvmStatic
    fun bindTwinkleRankingTvDate(textView: TextView, string: String?) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val formatted = current.format(formatter)
        textView.text = formatted + " 기준"
    }

    @BindingAdapter("bind_twinkle_ranking_list")
    @JvmStatic
    fun bindTwinkleRankingList(recyclerView: RecyclerView, items: MutableList<TwinkleRanking>) {
        if (recyclerView.adapter == null) {
            val adapter = CloverRankingAdapter()
            recyclerView.adapter = adapter
        }
        (recyclerView.adapter as CloverRankingAdapter).items = items
        recyclerView.adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("bind_accum_clover_history_clover")
    @JvmStatic
    fun bindAccumCloverHistoryClover(textView: TextView, clover: Int) {
        val text = "+ ${DecimalFormat("#,###").format(clover)} clover"
        textView.text = text
    }

    @BindingAdapter("bind_used_clover_history_clover")
    @JvmStatic
    fun bindUsedCloverHistoryClover(textView: TextView, clover: Int) {
        val text = "- ${DecimalFormat("#,###").format(clover)} clover"
        textView.text = text
    }

    @BindingAdapter("bind_accum_clover_history_work")
    @JvmStatic
    fun bindUsedCloverHistoryWork(textView: TextView, workTime: Int) {
        val text = "${workTime}시간 근무"
        textView.text = text
    }

    @BindingAdapter("bind_twinkle_detail_3dot")
    @JvmStatic
    fun bindTwinkleDetail3dot(imageView: ImageView, isWriter: String?) {
        if (isWriter == "Y")
            imageView.visibility = VISIBLE
        else
            imageView.visibility = INVISIBLE
    }

    @BindingAdapter("bind_my_page_working_year")
    @JvmStatic
    fun bindMyPageWorkingYear(textView: TextView, workingYear: Int) {
        val text = "${workingYear}년"
        textView.text = text
    }
}