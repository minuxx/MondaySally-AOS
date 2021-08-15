package com.moon.android.mondaysally.ui.main.twinkle.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.MyTwinkle
import com.moon.android.mondaysally.databinding.ItemMyTwinkleBinding

class MyTwinkleAdapter() :
    PagingDataAdapter<MyTwinkle, MyTwinkleAdapter.ViewHolder>(GiftHistoryDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemMyTwinkleBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_my_twinkle
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myTwinkle = getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemMyTwinkleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(myTwinkle: MyTwinkle) {
            binding.model = myTwinkle
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩
            binding.itemMyTwinkle.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(myTwinkle)
                }
            }
        }
    }

    private var onItemClickListener: ((MyTwinkle) -> Unit)? = null

    fun setOnItemClickListener(listener: (MyTwinkle) -> Unit) {
        onItemClickListener = listener
    }

    companion object GiftHistoryDiffUtil : DiffUtil.ItemCallback<MyTwinkle>() {
        override fun areItemsTheSame(oldItem: MyTwinkle, newItem: MyTwinkle): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MyTwinkle, newItem: MyTwinkle): Boolean {
            return oldItem == newItem
        }
    }
}