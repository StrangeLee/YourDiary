package com.strange.yourdiary.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import android.text.TextUtils
import android.view.View
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.db.AppDatabase
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    // Todo : exception handling (Ex - didn't write or choose any widget)
    private val calendar = Calendar.getInstance()

    private var db : AppDatabase? = null

    var curDate = calendar.get(Calendar.DATE).toString()
    var curDay = calendar.get(Calendar.DAY_OF_WEEK).toString()
    var curTime = SimpleDateFormat("HH:mm").format(calendar.time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar_add)

        // db instance 생성 및 insert
        db = AppDatabase.getInstance(this)

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

        val addRunnable = Runnable {
            val diary = DiaryData(
                id = 0,
                title = edit_add_title.text.toString(),
                content = edit_add_content.text.toString(),
                weather = "맑음",
                location = "대구 광역시",
                uploadDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
            )

            db?.diaryDao()?.insert(diary)
        }

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

                        val thread = Thread(addRunnable)
                        thread.start()

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

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}
