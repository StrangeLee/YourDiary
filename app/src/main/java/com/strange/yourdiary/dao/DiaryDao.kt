package com.strange.yourdiary.dao

import androidx.room.*
import com.strange.yourdiary.data.DiaryData

@Dao
interface DiaryDao {

    @Query("SELECT * FROM diary")
    fun getAll() : List<DiaryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(diary : DiaryData)

    @Update
    fun update(vararg  diary : DiaryData)

    @Delete
    fun deleteById(vararg diary : DiaryData)
}