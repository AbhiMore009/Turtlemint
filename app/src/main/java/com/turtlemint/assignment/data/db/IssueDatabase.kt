package com.turtlemint.assignment.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.turtlemint.assignment.domain.response.IssueDetails
import com.turtlemint.assignment.domain.response.IssuesResponse
import com.turtlemint.assignment.utils.Constants.ISSUE_DATABASE

@Database(entities = [IssuesResponse::class, IssueDetails::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class IssueDatabase : RoomDatabase() {

    abstract fun issueDao(): IssueDao
    abstract fun issueDetailsDao(): IssueDetailsDao


    companion object {
        private var instance: IssueDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): IssueDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, IssueDatabase::class.java,
                    ISSUE_DATABASE
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }

    }
}