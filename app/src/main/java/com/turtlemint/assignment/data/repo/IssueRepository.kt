package com.turtlemint.assignment.data.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turtlemint.assignment.R
import com.turtlemint.assignment.data.db.IssueDatabase
import com.turtlemint.assignment.domain.response.IssueDetails
import com.turtlemint.assignment.domain.response.IssuesDetailsResponse
import com.turtlemint.assignment.domain.response.IssuesResponse
import com.turtlemint.assignment.utils.AppUtil
import com.turtlemint.assignment.utils.Constants
import com.turtlemint.assignment.utils.NetworkResult
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class IssueRepository @Inject constructor(
    private val issueApi: IssueApi,
    @ApplicationContext val appContext: Context
) {

    private val _issueResponseLiveData = MutableLiveData<NetworkResult<List<JSONObject>>>()
    val issueResponseLiveData: LiveData<NetworkResult<List<JSONObject>>>
        get() = _issueResponseLiveData

    /*
  * getIssueList()
  *  check Internet Availability
  */
    suspend fun getIssueList() {
        if (AppUtil.isOnline(appContext)) {
            _issueResponseLiveData.postValue(NetworkResult.Loading())
            val response = issueApi.getIssueList()
            handleResponse(response, type = Constants.ISSUE_LIST)
        } else {
            if (IssueDatabase.getInstance(appContext).issueDao().getAllIssueCount() > 0) {
                getIssueData()
            } else {
                noInternetError()
            }
        }
    }


    /*
   * getIssueData()
   * get Local Issue Data
   */

    private suspend fun getIssueData() {
        _issueResponseLiveData.postValue(
            NetworkResult.Success(
                IssueDatabase.getInstance(appContext).issueDao()
                    .getAllIssues() as List<JSONObject>
            )
        )
    }

    /*
    * getIssueDetails(url)
    * check Internet availability
    * get Issue details for unique comments Url
    */

    suspend fun getIssueDetails(url: String) {
        if (AppUtil.isOnline(appContext)) {
            _issueResponseLiveData.postValue(NetworkResult.Loading())
            val response = issueApi.getIssueDetails(url)
            handleResponse(response, url, Constants.ISSUE_DETAILS)
        } else {
            if (IssueDatabase.getInstance(appContext).issueDetailsDao().getDetailsCount(url) > 0) {
                getIssueLocalDetails(url)
            } else {
                _issueResponseLiveData.postValue(NetworkResult.Error(appContext.getString(R.string.no_internet_connection)))
            }
        }
    }

    /*
     * getIssueLocalDetails(url)
     * get Issue details for unique comments Url from local DB
     */

    private suspend fun getIssueLocalDetails(url: String) {
        _issueResponseLiveData.postValue(
            NetworkResult.Success(
                appContext.let {
                    IssueDatabase.getInstance(it).issueDetailsDao()
                        .getIssueResponse(url).issuesDetailsResponse as List<JSONObject>
                }
            )
        )
    }


    private fun noInternetError() {
        _issueResponseLiveData.postValue(NetworkResult.Error(appContext.getString(R.string.no_internet_connection)))
    }


    private suspend fun <T : Any> handleResponse(
        response: Response<T>,
        url: String = "",
        type: String
    ) {
        if (response.isSuccessful && response.body() != null) {
            if (type == Constants.ISSUE_LIST) {
                setIssuesData(response.body() as List<IssuesResponse>)
                getIssueData()
            } else {
                setIssueDetails(url, (response.body() as List<IssuesDetailsResponse>))
                getIssueLocalDetails(url)
            }
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _issueResponseLiveData.postValue(NetworkResult.Error(errorObj.getString(Constants.MESSAGE)))
        } else {
            _issueResponseLiveData.postValue(NetworkResult.Error(appContext.getString(R.string.something_went_wrong)))
        }
    }


    /*
      * setIssueDetails(url: String, response: List<IssuesDetailsResponse>)
      * set Issue Details for comments URL
      */

    private suspend fun setIssueDetails(url: String, response: List<IssuesDetailsResponse>) {
        val issueDetails = IssueDetails(
            url = url,
            issuesDetailsResponse = response
        )
        if (IssueDatabase.getInstance(appContext).issueDetailsDao()
                .getDetailsCount(url) == 0
        ) {
            IssueDatabase.getInstance(appContext).issueDetailsDao().insert(issueDetails)
        } else {
            IssueDatabase.getInstance(appContext).issueDetailsDao().update(issueDetails)
        }
    }


    /*
      * setIssuesData(issuesResponseList: List<IssuesResponse>)
      *  get local Issue Details
      */

    private suspend fun setIssuesData(issuesResponseList: List<IssuesResponse>) {
        if (IssueDatabase.getInstance(appContext).issueDao()
                .getAllIssueCount() == 0
        ) {
            IssueDatabase.getInstance(appContext).issueDao()
                .insert(issuesResponseList)
        } else {
            IssueDatabase.getInstance(appContext).issueDao()
                .update(issuesResponseList)
        }
    }
}