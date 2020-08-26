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
import android.widget.Toast
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.databinding.DialogDiaryDetailBinding
import kotlinx.android.synthetic.main.dialog_diary_detail.*
import kotlinx.android.synthetic.main.dialog_diary_detail.view.*
import kotlinx.android.synthetic.main.dialog_todo_edit.view.*
import java.text.SimpleDateFormat

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

        binding.root.tv_dialog_location.setOnClickListener {
            Toast.makeText(context, diary.location, Toast.LENGTH_LONG).show()
        }

        convertData()
    }

    private fun convertData() {
        val transDate = SimpleDateFormat("yyyy-MM-dd").parse(diary.uploadDate)
        val time = diary.uploadDate.substring(11)

        Log.d("Data", "location length ${diary.location.length}")
        Log.d("Data", "before set location ${diary.location}")
        val location = if (diary.location.length > 15) {
            diary.location.substring(0, 15) + "..."
        } else {
            diary.location
        }

        Log.d("Data", "after set location $location")

        binding.root.tv_dialog_title.text = diary.title
        binding.root.tv_dialog_content.text = diary.content
        binding.root.tv_dialog_weather.text = diary.weather
        binding.root.tv_dialog_location.text = location
        binding.root.tv_dialog_month.text = SimpleDateFormat("MMMM").format(transDate)
        binding.root.tv_dialog_day.text = SimpleDateFormat("EEEE").format(transDate) + ", "
        binding.root.tv_dialog_date.text = SimpleDateFormat("dd").format(transDate)
        binding.root.tv_dialog_time.text = time
    }
}

