package com.strange.yourdiary.ui.main

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.strange.yourdiary.R
import com.strange.yourdiary.data.TodoData
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.lv_todo_list.view.*

class TodoAdapter(
    private val mContext: Context
) : BaseAdapter(){

    private var todoList = ArrayList<TodoData>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(R.layout.lv_todo_list, parent, false)

        val tv_title = view.findViewById<TextView>(R.id.tv_todo_title)
        val tv_content = view.findViewById<TextView>(R.id.tv_todo_content)
        val checkBox = view.findViewById<CheckBox>(R.id.cb_todo_list)

        tv_title.text = todoList[position].title
        tv_content.text = todoList[position].content
        checkBox.isSelected = false

        if (checkBox.isSelected) {
            tv_content.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
        } else {
            tv_content.setPaintFlags(0)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return todoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return todoList.size
    }

    fun addItem(todo : TodoData) {
        todoList.add(todo)
    }
}