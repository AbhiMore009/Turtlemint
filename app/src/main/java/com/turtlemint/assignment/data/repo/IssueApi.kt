package com.turtlemint.assignment.data.repo

import com.turtlemint.assignment.domain.response.listing_response.IssuesResponse
import org.json.JSONArray
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface IssueApi {
    @GET("/repos/square/okhttp/issues")
    suspend fun getIssueList() : Response<List<IssuesResponse>>

    @GET
    suspend fun getIssueDetails(@Url url: String): Response<List<IssuesResponse>>

}
