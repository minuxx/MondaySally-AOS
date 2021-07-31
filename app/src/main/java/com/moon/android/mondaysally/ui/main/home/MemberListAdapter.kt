package com.moon.android.mondaysally.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Member
import com.moon.android.mondaysally.databinding.ItemMemberBinding

class MemberListAdapter() : ListAdapter<Member, MemberListAdapter.ViewHolder>(MemberDiffUtil) {

    var items = mutableListOf<Member>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemMemberBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_member
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(member: Member) {
            binding.model = member
            binding.executePendingBindings()
        }
    }

    private var onItemClickListener: ((Member) -> Unit)? = null

    fun setOnItemClickListener(listener: (Member) -> Unit) {
        onItemClickListener = listener
    }

    companion object MemberDiffUtil : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem == newItem
        }
    }
}