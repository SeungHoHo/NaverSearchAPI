package com.seungho.naverapiex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seungho.naverapiex.databinding.ItemHistoryBinding
import com.seungho.naverapiex.room.History

class HistoryAdapter(val historyDeleteClickListener: (String) -> (Unit)) : ListAdapter<History, HistoryAdapter.Holder>(diffUtil) {

    inner class Holder(private val binding : ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            binding.historyKeywordTextView.text = history.keyword
            binding.historyKeywordDeleteButton.setOnClickListener {
                historyDeleteClickListener(history.keyword.orEmpty())
            }
            binding.historyKeywordTextView.setOnClickListener {
                textViewClickListener.onClick(it as TextView, position)
            }
        }

    }

    interface OnTextViewClickListener {
        fun onClick(t: TextView, position: Int)
    }

    fun setTextViewClickListener(onTextViewClickListener: OnTextViewClickListener) {
        this.textViewClickListener = onTextViewClickListener
    }

    private lateinit var textViewClickListener : OnTextViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: History, newItem: History) =
                oldItem.uid == newItem.uid

        }
    }
}