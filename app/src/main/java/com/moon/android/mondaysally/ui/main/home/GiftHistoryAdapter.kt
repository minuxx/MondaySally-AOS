package com.moon.android.mondaysally.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.GiftHistory
import com.moon.android.mondaysally.databinding.ItemGiftHistoryBinding

class GiftHistoryAdapter() : ListAdapter<GiftHistory, GiftHistoryAdapter.ViewHolder>(GiftHistoryDiffUtil) {

    var items = mutableListOf<GiftHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemGiftHistoryBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_gift_history
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemGiftHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(giftHistory: GiftHistory) {
            binding.model = giftHistory
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩
            binding.itemGiftHistoryIv.setOnClickListener {
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

    companion object GiftHistoryDiffUtil : DiffUtil.ItemCallback<GiftHistory>() {
        override fun areItemsTheSame(oldItem: GiftHistory, newItem: GiftHistory): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GiftHistory, newItem: GiftHistory): Boolean {
            return oldItem == newItem
        }
    }
}