package com.turtlemint.assignment.domain.response

import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.turtlemint.assignment.data.db.DataConverter


@TypeConverters(DataConverter::class)
data class IssuesDetailsResponse(
    @PrimaryKey(autoGenerate = false) val primaryKeyId: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("user") var user: User? = User(),
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("closed_at") var closedAt: String? = null,
    @SerializedName("body") var body: String? = null,
)