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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.turtlemint.assignment.R
import com.turtlemint.assignment.databinding.FragmentIssueListBinding
import com.turtlemint.assignment.domain.response.IssuesResponse
import com.turtlemint.assignment.presentation.adapter.IssueListAdapter
import com.turtlemint.assignment.utils.Constants.ISSUE_RESPONSE
import com.turtlemint.assignment.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssueListFragment : Fragment() {

    var _binding: FragmentIssueListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: IssueListAdapter

    private val viewModel by viewModels<IssueListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_issue_list, container, false
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getIssueList()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this

        binding.recList.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        adapter = IssueListAdapter(::onIssueClicked)
        binding.recList.adapter = adapter

        bindObservers()
    }


    private fun onIssueClicked(issuesResponse: IssuesResponse) {
        if ((issuesResponse.comments ?: 0) > 0) {
            val bundle = Bundle()
            bundle.putString(ISSUE_RESPONSE, Gson().toJson(issuesResponse))
            findNavController().navigate(
                R.id.action_FragmentIssueList_to_issueDetailsFragment,
                bundle
            )
        }
    }

    private fun bindObservers() {
        viewModel.listResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data as MutableList<IssuesResponse>)

                }
                is NetworkResult.Error -> {
                    if(it.message.equals(getString(R.string.no_internet_connection)))
                    {
                        binding.tvError.isVisible=true
                        binding.tvError.text=it.message
                    }
                    else{
                        Toast.makeText(this.context,it.message,Toast.LENGTH_SHORT).show()
                    }
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}