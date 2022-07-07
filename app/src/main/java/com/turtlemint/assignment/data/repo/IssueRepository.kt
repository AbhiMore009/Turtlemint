package com.turtlemint.assignment.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtlemint.assignment.domain.response.listing_response.IssuesResponse
import com.turtlemint.assignment.utils.NetworkResult
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class IssueRepository @Inject constructor(private val issueApi: IssueApi) {

    private val _issueResponseLiveData = MutableLiveData<NetworkResult<List<IssuesResponse>>>()
    val issueResponseLiveData: LiveData<NetworkResult<List<IssuesResponse>>>
        get() = _issueResponseLiveData

    suspend fun getIssueList() {
        _issueResponseLiveData.postValue(NetworkResult.Loading())
        val response = issueApi.getIssueList()
        handleResponse(response)
    }

    suspend fun getIssueDetails(url: String) {
        _issueResponseLiveData.postValue(NetworkResult.Loading())
        val response = issueApi.getIssueDetails(url)
        handleResponse(response)
    }


    private fun handleResponse(response: Response<List<IssuesResponse>>) {
        if (response.isSuccessful && response.body() != null) {
            _issueResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody()!=null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _issueResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        }
        else{
            _issueResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}