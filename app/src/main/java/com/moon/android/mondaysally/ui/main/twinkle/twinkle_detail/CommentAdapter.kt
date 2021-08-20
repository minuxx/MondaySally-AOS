package com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.TwinkleComment
import com.moon.android.mondaysally.databinding.ItemCommentBindingImpl

class CommentAdapter() : ListAdapter<TwinkleComment, CommentAdapter.ViewHolder>(TwinkleCommentDiffUtil) {

    var items = mutableListOf<TwinkleComment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemCommentBindingImpl>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_comment
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: ItemCommentBindingImpl) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(TwinkleComment: TwinkleComment) {
            binding.model = TwinkleComment
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩
//            binding.itemTwinkleCommentIv.setOnClickListener {
//                onItemClickListener?.let { click ->
//                    click(TwinkleComment)
//                }
//            }
        }
    }

    private var onItemClickListener: ((TwinkleComment) -> Unit)? = null

    fun setOnItemClickListener(listener: (TwinkleComment) -> Unit) {
        onItemClickListener = listener
    }

    companion object TwinkleCommentDiffUtil : DiffUtil.ItemCallback<TwinkleComment>() {
        override fun areItemsTheSame(oldItem: TwinkleComment, newItem: TwinkleComment): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TwinkleComment, newItem: TwinkleComment): Boolean {
            return oldItem == newItem
        }
    }
}