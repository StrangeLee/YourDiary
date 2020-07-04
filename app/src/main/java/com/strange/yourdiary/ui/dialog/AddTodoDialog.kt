package com.strange.yourdiary.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.data.TodoData
import com.strange.yourdiary.db.AppDatabase
import kotlinx.android.synthetic.main.dialog_diary_detail.*
import kotlinx.android.synthetic.main.dialog_todo_edit.*
import org.jetbrains.anko.toast
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddTodoDialog(context: Context) : Dialog(context) {

    // room db 생성
    private var db : AppDatabase? = null
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_todo_edit)

        db = AppDatabase.getInstance(context)

        btn_todo_dialog_cancel.setOnClickListener { dismiss() }

        btn_todo_dialog_ok.setOnClickListener {
            if (et_dialog_todo_content.text.toString() == "" || et_dialog_todo_title.text.toString() == "") {
                context.toast("제목 또는 내용이 입력되지 않았습니다.")
            } else {
                try {
                    Log.i("TODO", SimpleDateFormat("yyyy-MM-dd").format(cal.time))
                    val runnable = Runnable {
                        val todo = TodoData(
                            id = 0,
                            content = et_dialog_todo_content.text.toString(),
                            title = et_dialog_todo_title.text.toString(),
                            date = SimpleDateFormat("yyyy-MM-dd").format(cal.time),
                            checked = false
                        )
                        db?.todoDao()?.insert(todo)
                    }

                    val thread = Thread(runnable)
                    thread.start()
                    dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    AppDatabase.destroyInstance()
                }
            }
        }

    }


}