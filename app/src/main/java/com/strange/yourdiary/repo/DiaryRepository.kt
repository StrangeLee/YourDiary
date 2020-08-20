package com.strange.yourdiary.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.db.AppDatabase
import java.lang.Exception

class DiaryRepository(application: Application) {

    private val database = AppDatabase.getInstance(application)!!
    private val diaryDao = database.diaryDao()
    private val diaryList : LiveData<List<DiaryData>> = diaryDao.getAll()

    fun getAll() : LiveData<List<DiaryData>> {
        return diaryList
    }

    fun insert(diary : DiaryData) {
        try {
            val thread = Thread(Runnable {
                diaryDao.insert(diary) })
            thread.start()
        } catch (e: Exception) {
            Log.e("Error", e.message)
        }
    }

    fun delete(diary : DiaryData) {
        try {
            val thread = Thread(Runnable {
                diaryDao.deleteById(diary) })
            thread.start()
        } catch (e: Exception) {}
    }
}