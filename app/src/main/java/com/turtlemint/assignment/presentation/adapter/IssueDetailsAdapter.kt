package com.turtlemint.assignment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtlemint.assignment.databinding.RecDetailsItemBinding
import com.turtlemint.assignment.domain.response.IssuesDetailsResponse


class IssueDetailsAdapter :
    ListAdapter<IssuesDetailsResponse, IssueDetailsAdapter.IssueViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding =
            RecDetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class IssueViewHolder(private val binding: RecDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(issueDetail: IssuesDetailsResponse) {
            binding.issueDetail = issueDetail
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<IssuesDetailsResponse>() {
        override fun areItemsTheSame(
            oldItem: IssuesDetailsResponse,
            newItem: IssuesDetailsResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: IssuesDetailsResponse,
            newItem: IssuesDetailsResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}