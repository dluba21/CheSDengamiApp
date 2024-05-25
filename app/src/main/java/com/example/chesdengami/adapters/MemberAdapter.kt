package com.example.chesdengami.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chesdengami.databinding.MemberItemBinding
import com.example.chesdengami.listeners.OnItemClickListener
import com.example.chesdengami.model.Member

class MemberAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<Member, MemberAdapter.MemberViewHolder>(MEMBER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        return MemberViewHolder.create(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class MemberViewHolder(private val binding: MemberItemBinding, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(member: Member) {
            with(binding) {
                memberItemTextView.text = member.memberName
                memberItemTextView.setOnClickListener {
                    listener.onItemClick(member.id)
                }
            }
        }
        companion object {
            fun create(parent: ViewGroup, onItemClickListener: OnItemClickListener): MemberViewHolder {
                return MemberViewHolder(MemberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClickListener)
            }
        }
    }

    companion object {
        private var MEMBER_COMPARATOR = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem.memberName == newItem.memberName
            }
        }
    }
}