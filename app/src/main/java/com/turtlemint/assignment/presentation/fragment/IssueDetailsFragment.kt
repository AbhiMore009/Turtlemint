package com.turtlemint.assignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.turtlemint.assignment.R
import com.turtlemint.assignment.databinding.FragmentIssueDetailsBinding
import com.turtlemint.assignment.domain.response.IssueDetails
import com.turtlemint.assignment.domain.response.IssuesDetailsResponse
import com.turtlemint.assignment.domain.response.IssuesResponse
import com.turtlemint.assignment.presentation.adapter.IssueDetailsAdapter
import com.turtlemint.assignment.utils.Constants.ISSUE_RESPONSE
import com.turtlemint.assignment.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssueDetailsFragment : Fragment() {

    private lateinit var binding: FragmentIssueDetailsBinding
    private lateinit var adapter: IssueDetailsAdapter

    private val viewModel by viewModels<IssueListViewModel>()
    private lateinit var issuesResponse: IssuesResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_issue_details, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this

        this.arguments?.let {
            issuesResponse =
                Gson().fromJson(it.getString(ISSUE_RESPONSE), IssuesResponse::class.java)
            viewModel.getIssueDetails(issuesResponse.commentsUrl ?: "")
            binding.issueDetail=  Gson().fromJson(it.getString(ISSUE_RESPONSE), IssuesDetailsResponse::class.java)
        }


        binding.recDetails.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        adapter = IssueDetailsAdapter()
        binding.recDetails.adapter = adapter

        bindObservers()
    }


    private fun bindObservers() {
        viewModel.listResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data as MutableList<IssuesDetailsResponse>?)

                }
                is NetworkResult.Error -> {
                    if (it.message.equals(getString(R.string.no_internet_connection))) {
                        binding.tvError.isVisible = true
                        binding.tvError.text = it.message
                    } else {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }
}