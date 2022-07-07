package com.turtlemint.assignment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtlemint.assignment.databinding.RecListItemBinding
import com.turtlemint.assignment.domain.response.listing_response.IssuesResponse


class IssueListAdapter(private val onNoteClicked: (IssuesResponse) -> Unit) :
    ListAdapter<IssuesResponse, IssueListAdapter.IssueViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding = RecListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class IssueViewHolder(private val binding: RecListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(issuesResponse: IssuesResponse) {
            binding.issuesResponse=issuesResponse
            binding.root.setOnClickListener {
                onNoteClicked(issuesResponse)
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<IssuesResponse>() {
        override fun areItemsTheSame(oldItem: IssuesResponse, newItem: IssuesResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: IssuesResponse, newItem: IssuesResponse): Boolean {
            return oldItem == newItem
        }
    }
}