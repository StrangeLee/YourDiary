package com.strange.yourdiary.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil

import com.strange.yourdiary.R
import com.strange.yourdiary.data.TodoData
import com.strange.yourdiary.databinding.FragmentCalendarBinding
import com.strange.yourdiary.db.AppDatabase
import com.strange.yourdiary.event.CalenderSwipeEvent
import com.strange.yourdiary.widget.dialog.AddTodoDialog
import com.strange.yourdiary.widget.listview.adapter.TodoAdapter
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.fragment_calendar.view.tv_todo_edit

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    lateinit var binding: FragmentCalendarBinding
    lateinit var todoAdapter: TodoAdapter
    lateinit var todoList : List<TodoData>

    private var db : AppDatabase? = null
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        db = AppDatabase.getInstance(context!!)

        // calendar view swipe event
        binding.root.calendar_box.setOnTouchListener(object : CalenderSwipeEvent(context!!){
            override fun onSwipeRight() {
                super.onSwipeRight()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
            }
        })

        binding.root.tv_calendar_month.text = SimpleDateFormat("MMMM").format(cal.time)
        binding.root.tv_calendar_date.text = SimpleDateFormat("dd").format(cal.time)
        binding.root.tv_calendar_day.text = SimpleDateFormat("EEEE").format(cal.time)

        // To do list 세팅
        val runnable = Runnable {
            try {
                todoList = db?.todoDao()?.getTodoByDate(SimpleDateFormat("yyyy-MM-dd").format(cal.time))!!
                // adapter setting
                todoAdapter =
                    TodoAdapter(
                        context!!
                    )
                activity!!.runOnUiThread {
                    todoList.forEach {
                        todoAdapter.addItem(it)
                        Log.i("TODO", "all " + it.title + " ${it.date}")
                    }
                    binding.root.lv_todo.adapter = todoAdapter // adapter setting
                    Log.d("TODO", "Adapter Count ${todoAdapter.count}")
                }
            } catch (e : Exception) {
                Log.d("DiaryList", "Error - $e")
            }
        }

        val thread = Thread(runnable)
        thread.start()
//        todoAdapter.addItem(TodoData(
//            1,
//            "기능 타임어택",
//            "2학년 애들 타임 어택 시키기",
//            false,
//            "2020-07-01"
//        ))
//
//        todoAdapter.addItem(TodoData(
//            2,
//            "동아리 준비",
//            "모던패밀리 1S22E 준비하기",
//            false,
//            "2020-07-01"
//        ))
//
//        todoAdapter.addItem(TodoData(
//            3,
//            "빨래하기",
//            "기숙사 올라가서 빨래하기",
//            false,
//            "2020-07-02"
//        ))
//        view.lv_todo.adapter = todoAdapter // adapter setting


        binding.root.tv_todo_edit.setOnClickListener {
            val dialog = AddTodoDialog(context!!)
            dialog.show()
        }

        return binding.root
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}
