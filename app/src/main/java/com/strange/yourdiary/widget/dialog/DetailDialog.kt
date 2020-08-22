package com.strange.yourdiary.widget.dialog

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.databinding.DialogDiaryDetailBinding
import kotlinx.android.synthetic.main.dialog_diary_detail.*
import kotlinx.android.synthetic.main.dialog_diary_detail.view.*
import java.text.SimpleDateFormat

// 2020/08/22 Todo : 날짜 처리 하기
class DetailDialog(context: Context, private val diary: DiaryData) : Dialog(context) {

    private lateinit var binding: DialogDiaryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_diary_detail, null, false)
        setContentView(binding.root)

        binding.diary = diary

        binding.root.tv_dialog_close.setOnClickListener {
            dismiss()
        }

        Log.d("Data", diary.content)
        Log.d("Data", diary.location)
        Log.d("Data", diary.uploadDate.substring(1, 10))
        Log.d("Data", diary.uploadDate.substring(11))

        binding.root.tv_dialog_title.text = diary.title
        binding.root.tv_dialog_content.text = diary.content
        binding.root.tv_dialog_location.text = diary.location
    }
}

