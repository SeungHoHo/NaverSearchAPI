package com.seungho.naverapiex.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history") //조회
    fun getAll(): List<History>

    @Insert //삽입
    fun insertHistory(history: History)

    @Query("DELETE FROM history WHERE keyword = :keyword") //삭제
    fun delete(keyword: String)

}