package com.strange.yourdiary.ui.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import com.strange.yourdiary.R
import com.strange.yourdiary.data.TodoData
import com.strange.yourdiary.event.CalenderSwipeEvent
import com.strange.yourdiary.ui.main.TodoAdapter
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.lv_todo_list.view.*
import org.jetbrains.anko.support.v4.toast


class CalendarFragment : Fragment() {

    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_calendar, container, false)

        // calendar view swipe event
        view.calendar_box.setOnTouchListener(object : CalenderSwipeEvent(context!!){
            override fun onSwipeRight() {
                super.onSwipeRight()
                toast("right")
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                toast("left")
            }
        })

        view.tv_todo_edit.setOnClickListener {
            toast("Edit!")
        }

        // To do list 세팅
        todoAdapter = TodoAdapter(context!!)
        todoAdapter.addItem(TodoData(
            1,
            "기능 타임어택",
            "2학년 애들 타임 어택 시키기"
        ))

        todoAdapter.addItem(TodoData(
            2,
            "동아리 준비",
            "모던패밀리 1S22E 준비하기"
        ))

        todoAdapter.addItem(TodoData(
            3,
            "빨래하기",
            "기숙사 올라가서 빨래하기"
        ))

        view.lv_todo.adapter = todoAdapter

        return view
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }
}
