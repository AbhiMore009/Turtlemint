package com.turtlemint.assignment.domain.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.turtlemint.assignment.data.db.DataConverter


@Entity(tableName = "issue_details")
@TypeConverters(DataConverter::class)
data class IssueDetails(
    @PrimaryKey(autoGenerate = false) val primaryKeyId: Int? = null,
    val url: String,
    @SerializedName("issuesDetailsResponse") var issuesDetailsResponse: List<IssuesDetailsResponse> = ArrayList(),
)
