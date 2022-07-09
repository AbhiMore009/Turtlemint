package com.turtlemint.assignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.turtlemint.assignment.domain.response.IssueDetails

@Dao
interface IssueDetailsDao {

    @Insert
    suspend fun insert(issuesResponse: IssueDetails)

    @Update
    suspend fun update(issuesResponse: IssueDetails)

    @Query("select * from issue_details where url=:url")
    suspend fun getIssueResponse(url: String): IssueDetails

    @Query("select count(*) from issue_details where url= :url")
    suspend fun getDetailsCount(url: String): Int

}