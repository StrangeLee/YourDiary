package com.strange.yourdiary.ui.add

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import android.text.TextUtils
import android.view.View
import android.widget.PopupMenu
import com.strange.yourdiary.data.EmotionData
import com.strange.yourdiary.ui.main.MainActivity
import com.strange.yourdiary.R
import kotlinx.android.synthetic.main.activity_add.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    // Todo : exception handling (Ex - didn't write or choose any widget)
    private val calendar = Calendar.getInstance()

    var curDate = calendar.get(Calendar.DATE).toString()
    var curDay = calendar.get(Calendar.DAY_OF_WEEK).toString()
    var curTime = SimpleDateFormat("HH:mm").format(calendar.time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar_add)

        tv_add_date.text = SimpleDateFormat("yyyy년 MM월 dd일 E").format(calendar.time).toString()
    }

    // finish write diary's content
    fun writeDiary(view : View) {
        val diaryTitle = edit_add_title.text
        val diaryContent = edit_add_content.text

        if (TextUtils.isEmpty(diaryTitle) || diaryContent == null) {
            showDialog(getString(R.string.add_diary_null_exception))
        } else {
            showDialog(getString(R.string.add_diary_complete))
        }
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(ContextThemeWrapper(this,
            R.style.Theme_AppCompat_Dialog
        ))
        if (message == getString(R.string.add_diary_null_exception)) {
            builder.run {
                setTitle("일기 저장")
                    .setMessage(message)
                    .setPositiveButton("확인") {dialog, which ->  }
                    .show()
            }
        } else {
            builder.run {
                setTitle("일기 저장")
                    .setMessage(message)
                    .setPositiveButton("확인") {dialog, which ->
                        val intent = Intent(this@AddActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    .setNegativeButton("취소") {dialog, which ->
                    }
                    .show()
            }
        }
    }
}
