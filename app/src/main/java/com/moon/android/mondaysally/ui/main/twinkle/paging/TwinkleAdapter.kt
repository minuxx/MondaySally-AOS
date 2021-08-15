package com.moon.android.mondaysally.ui.main.twinkle.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Twinkle
import com.moon.android.mondaysally.databinding.ItemTwinkleBinding

class TwinkleAdapter() :
    PagingDataAdapter<Twinkle, TwinkleAdapter.ViewHolder>(GiftHistoryDiffUtil) {

    //    var items = mutableListOf<Twinkle>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemTwinkleBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_twinkle
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(twinkle = getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemTwinkleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(twinkle: Twinkle) {
            binding.model = twinkle
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩
            binding.itemTwinkle.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(twinkle)
                }
            }
        }
    }

    private var onItemClickListener: ((Twinkle) -> Unit)? = null

    fun setOnItemClickListener(listener: (Twinkle) -> Unit) {
        onItemClickListener = listener
    }

    companion object GiftHistoryDiffUtil : DiffUtil.ItemCallback<Twinkle>() {
        override fun areItemsTheSame(oldItem: Twinkle, newItem: Twinkle): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Twinkle, newItem: Twinkle): Boolean {
            return oldItem == newItem
        }
    }
}