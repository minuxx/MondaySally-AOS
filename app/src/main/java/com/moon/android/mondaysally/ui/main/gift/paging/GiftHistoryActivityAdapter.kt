package com.moon.android.mondaysally.ui.main.gift.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.GiftHistory
import com.moon.android.mondaysally.databinding.ItemGiftHistoryActivityBinding

class GiftHistoryActivityAdapter() :
    PagingDataAdapter<GiftHistory, GiftHistoryActivityAdapter.ViewHolder>(GiftDiffUtil) {

    var items = mutableListOf<GiftHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemGiftHistoryActivityBinding>(
                layoutInflater,
                viewType,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_gift_history_activity
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(giftHistory = getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemGiftHistoryActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(giftHistory: GiftHistory) {
            binding.model = giftHistory
            binding.executePendingBindings()
            binding.itemGiftHistory.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(giftHistory)
                }
            }
        }
    }

    private var onItemClickListener: ((GiftHistory) -> Unit)? = null

    fun setOnItemClickListener(listener: (GiftHistory) -> Unit) {
        onItemClickListener = listener
    }

    companion object GiftDiffUtil : DiffUtil.ItemCallback<GiftHistory>() {
        override fun areItemsTheSame(oldItem: GiftHistory, newItem: GiftHistory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GiftHistory, newItem: GiftHistory): Boolean {
            return oldItem == newItem
        }
    }
}