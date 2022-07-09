package com.turtlemint.assignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.turtlemint.assignment.domain.response.IssuesResponse

@Dao
interface IssueDao {

    @Insert
    suspend fun insert(issuesResponse: List<IssuesResponse>)

    @Update
    suspend fun update(issuesResponse: List<IssuesResponse>)

    @Query("select * from issue_table")
    suspend fun getAllIssues(): List<IssuesResponse>

    @Query("select count(*) from issue_table")
    suspend fun getAllIssueCount(): Int

}