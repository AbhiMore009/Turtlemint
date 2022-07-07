package com.turtlemint.assignment.presentation.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtlemint.assignment.data.repo.IssueRepository
import com.turtlemint.assignment.domain.response.listing_response.IssuesResponse
import com.turtlemint.assignment.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueListViewModel @Inject constructor(private val issueRepository: IssueRepository) : ViewModel() {

    val listResponseLiveData: LiveData<NetworkResult<List<IssuesResponse>>>
        get() = issueRepository.issueResponseLiveData


    fun getIssueList(){
        viewModelScope.launch {
            issueRepository.getIssueList()
        }
    }

    fun getIssueDetails(url: String) {
        viewModelScope.launch {
            issueRepository.getIssueDetails(url)
        }    }

}