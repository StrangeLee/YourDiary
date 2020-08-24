package com.strange.yourdiary.viewmodel

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.repo.DiaryRepository
import com.strange.yourdiary.view.activity.MainActivity

class DiaryViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = DiaryRepository(application)
    private val diaryList = repository.getAll()

    fun getAll() : LiveData<List<DiaryData>> {
        return this.diaryList
    }

    fun insert(diary: DiaryData) {
        repository.insert(diary)
    }

    fun delete(diary: DiaryData) {
        repository.delete(diary)
    }
}