package com.moon.android.mondaysally.ui.main.gift.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Gift
import com.moon.android.mondaysally.databinding.ItemGiftShopBinding

class GiftShopAdapter() : PagingDataAdapter<Gift, GiftShopAdapter.ViewHolder>(GiftDiffUtil) {

    var items = mutableListOf<Gift>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemGiftShopBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_gift_shop
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(giftShop = getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemGiftShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(giftShop: Gift) {
            binding.model = giftShop
            binding.executePendingBindings()
            binding.itemGiftShopIv.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(giftShop)
                }
            }
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