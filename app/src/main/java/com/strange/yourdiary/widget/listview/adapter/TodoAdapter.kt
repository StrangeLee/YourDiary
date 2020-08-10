package com.strange.yourdiary.widget.listview.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.strange.yourdiary.R
import com.strange.yourdiary.data.TodoData
import com.strange.yourdiary.widget.listview.holder.TodoViewHolder
import kotlinx.android.synthetic.main.lv_todo_list.view.*

class TodoAdapter(
    private val mContext: Context
) : BaseAdapter(){

    private var todoList = ArrayList<TodoData>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view : View
        val holder : TodoViewHolder

        view = convertView ?: layoutInflater.inflate(R.layout.lv_todo_list, parent, false)

        holder = TodoViewHolder(view)
        holder.bind(todoList[position])

        // checkbox 선택시 event
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.tv_content.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.tv_title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.tv_content.paintFlags = 0
                holder.tv_title.paintFlags = 0
            }
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


