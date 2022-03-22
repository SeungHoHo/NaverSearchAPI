package com.seungho.naverapiex

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seungho.naverapiex.room.History
import com.seungho.naverapiex.room.HistoryDao

@Database(entities = [History::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}