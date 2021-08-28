package com.moon.android.mondaysally.ui.main.clover.clover_history.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.CloverHistory
import com.moon.android.mondaysally.databinding.ItemUsedCloverHistoryBinding

class UsedCloverHistoryAdapter() :
    PagingDataAdapter<CloverHistory, UsedCloverHistoryAdapter.ViewHolder>(GiftDiffUtil) {

    var items = mutableListOf<CloverHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemUsedCloverHistoryBinding>(
                layoutInflater,
                viewType,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_used_clover_history
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cloverHistory = getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemUsedCloverHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cloverHistory: CloverHistory) {
            binding.model = cloverHistory
            binding.executePendingBindings()
        }
    }

    private var onItemClickListener: ((CloverHistory) -> Unit)? = null

    fun setOnItemClickListener(listener: (CloverHistory) -> Unit) {
        onItemClickListener = listener
    }

    companion object GiftDiffUtil : DiffUtil.ItemCallback<CloverHistory>() {
        override fun areItemsTheSame(oldItem: CloverHistory, newItem: CloverHistory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CloverHistory, newItem: CloverHistory): Boolean {
            return oldItem == newItem
        }
    }
}