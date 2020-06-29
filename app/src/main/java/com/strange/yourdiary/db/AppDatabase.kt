package com.strange.yourdiary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.strange.yourdiary.dao.DiaryDao
import com.strange.yourdiary.data.DiaryData

@Database(entities = [DiaryData::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun diaryDao() : DiaryDao

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "diary.db")
                        .fallbackToDestructiveMigration()
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