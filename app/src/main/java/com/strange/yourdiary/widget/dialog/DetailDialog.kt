package com.strange.yourdiary.widget.dialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.databinding.DialogDiaryDetailBinding
import kotlinx.android.synthetic.main.dialog_diary_detail.*
import kotlinx.android.synthetic.main.dialog_diary_detail.view.*

// 2020/08/11 Todo : Binding 이 제대로 되지 않는 오류
class DetailDialog(context: Context, private val diary: DiaryData) : Dialog(context) {

    private lateinit var binding: DialogDiaryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_diary_detail, null, false)

        binding.diary = diary
        setContentView(binding.root)

        binding.root.tv_dialog_close.setOnClickListener {
            dismiss()
        }

        Log.d("Data", diary.content)
        Log.d("Data", diary.location)

        binding.root.tv_dialog_title.text = diary.title
    }
}