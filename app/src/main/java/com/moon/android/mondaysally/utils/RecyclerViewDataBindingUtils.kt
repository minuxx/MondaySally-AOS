package com.moon.android.mondaysally.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.GiftHistory
import com.moon.android.mondaysally.data.entities.Member
import com.moon.android.mondaysally.ui.main.home.GiftHistoryAdapter
import com.moon.android.mondaysally.ui.main.home.MemberListAdapter

object RecyclerViewDataBindingUtils {

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
                .load(url).placeholder(R.drawable.bg_round_white_gray)
                .error(R.drawable.bg_round_white_gray)
                .centerCrop()
//                .transform(CenterCrop(), RoundedCorners(30))
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
}