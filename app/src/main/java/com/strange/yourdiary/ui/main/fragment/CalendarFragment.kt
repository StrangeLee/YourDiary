package com.strange.yourdiary.ui.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import com.strange.yourdiary.R
import com.strange.yourdiary.data.TodoData
import com.strange.yourdiary.ui.main.TodoAdapter
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.lv_todo_list.view.*


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

        todoAdapter = TodoAdapter(context!!)
        todoAdapter.addItem(TodoData(
            1,
            "기능 타임어택",
            "2학년 애들 타임 어택 시키기"
        ))

        view.lv_todo.adapter = todoAdapter

        return view
    }

}
