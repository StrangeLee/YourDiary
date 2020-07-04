package com.strange.yourdiary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.strange.yourdiary.dao.DiaryDao
import com.strange.yourdiary.dao.TodoDao
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.data.TodoData

// database 를 수정한 후에는 version 또한 수정해야함 ㅎㅎ
@Database(entities = [DiaryData::class, TodoData::class],
    version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun diaryDao() : DiaryDao
    abstract fun todoDao(): TodoDao

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "diary.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}