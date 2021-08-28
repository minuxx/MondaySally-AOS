package com.moon.android.mondaysally.ui.main.clover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.TwinkleRanking
import com.moon.android.mondaysally.databinding.ItemTwinkleRankingBinding

class CloverRankingAdapter() :
    PagingDataAdapter<TwinkleRanking, CloverRankingAdapter.ViewHolder>(TwinkleRankingDiffUtil) {

    var items = mutableListOf<TwinkleRanking>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemTwinkleRankingBinding>(
                layoutInflater,
                viewType,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_twinkle_ranking
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(twinkleRanking = getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemTwinkleRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(twinkleRanking: TwinkleRanking) {
            binding.model = twinkleRanking
            binding.executePendingBindings()
        }
    }

    private var onItemClickListener: ((TwinkleRanking) -> Unit)? = null

    fun setOnItemClickListener(listener: (TwinkleRanking) -> Unit) {
        onItemClickListener = listener
    }

    companion object TwinkleRankingDiffUtil : DiffUtil.ItemCallback<TwinkleRanking>() {
        override fun areItemsTheSame(oldItem: TwinkleRanking, newItem: TwinkleRanking): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TwinkleRanking, newItem: TwinkleRanking): Boolean {
            return oldItem == newItem
        }
    }
}