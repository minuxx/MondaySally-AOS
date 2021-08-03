package com.moon.android.mondaysally.ui.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Gift
import com.moon.android.mondaysally.databinding.ItemGiftShopBinding

class GiftShopAdapter() : ListAdapter<Gift, GiftShopAdapter.ViewHolder>(GiftDiffUtil) {

    var items = mutableListOf<Gift>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemGiftShopBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_gift_shop
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemGiftShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(member: Gift) {
            binding.model = member
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