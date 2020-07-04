package com.strange.yourdiary.dao

import androidx.room.*
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.data.TodoData

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun getAll() : List<TodoData>

    @Query("SELECT * FROM todo where date = :date")
    fun getTodoByDate(date: String) : List<TodoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo : TodoData)

    @Update
    fun update(vararg  todo : TodoData)

    @Delete
    fun deleteById(vararg todo : TodoData)

    @Query("delete from todo where date = :date")
    fun deleteAllByDate(date: String)
}