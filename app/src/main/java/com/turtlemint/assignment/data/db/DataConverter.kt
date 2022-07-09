package com.turtlemint.assignment.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.turtlemint.assignment.domain.response.*

class DataConverter {

    @TypeConverter
    fun fromUserListToString(value: User): String {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toUserListFromString(value: String): User {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromReactionsListToString(value: Reactions): String {
        val gson = Gson()
        val type = object : TypeToken<Reactions>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toReactionsListFromString(value: String): Reactions {
        val gson = Gson()
        val type = object : TypeToken<Reactions>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromIssuesDetailsResponseToString(value: List<IssuesDetailsResponse>): String {
        val gson = Gson()
        val type = object : TypeToken<List<IssuesDetailsResponse>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIssuesDetailsResponseFromString(value: String): List<IssuesDetailsResponse> {
        val gson = Gson()
        val type = object : TypeToken<List<IssuesDetailsResponse>>() {}.type
        return gson.fromJson(value, type)
    }

}