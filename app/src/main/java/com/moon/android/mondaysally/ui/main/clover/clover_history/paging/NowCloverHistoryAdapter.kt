package com.moon.android.mondaysally.ui.main.clover.clover_history.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Gift
import com.moon.android.mondaysally.databinding.ItemGiftInCloverHistoryBinding

class NowCloverHistoryAdapter() :
    PagingDataAdapter<Gift, NowCloverHistoryAdapter.ViewHolder>(GiftDiffUtil) {

    var items = mutableListOf<Gift>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemGiftInCloverHistoryBinding>(
                layoutInflater,
                viewType,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_gift_in_clover_history
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gift = getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemGiftInCloverHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gift: Gift) {
            binding.model = gift
            binding.executePendingBindings()
        }
    }

    private var onItemClickListener: ((Gift) -> Unit)? = null

    fun setOnItemClickListener(listener: (Gift) -> Unit) {
        onItemClickListener = listener
    }

    companion object GiftDiffUtil : DiffUtil.ItemCallback<Gift>() {
        override fun areItemsTheSame(oldItem: Gift, newItem: Gift): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Gift, newItem: Gift): Boolean {
            return oldItem == newItem
        }
    }
}