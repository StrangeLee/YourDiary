package com.strange.yourdiary.ui.main

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.data.TodoData
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.lv_todo_list.view.*
import kotlinx.android.synthetic.main.rlv_diary_list.view.*
import org.jetbrains.anko.toast

class TodoAdapter(
    private val mContext: Context
) : BaseAdapter(){

    private var todoList = ArrayList<TodoData>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view : View
        val holder : TodoViewHolder

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.lv_todo_list, parent, false)
        } else {
            view = convertView
        }

        holder = TodoViewHolder(view)
        holder.bind(todoList[position])

//        val tv_title = view.findViewById<TextView>(R.id.tv_todo_title)
//        val tv_content = view.findViewById<TextView>(R.id.tv_todo_content)
//        val checkBox = view.findViewById<CheckBox>(R.id.cb_todo_list)
//
//        tv_title.text = todoList[position].title
//        tv_content.text = todoList[position].content
//        checkBox.isSelected = false
//
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
//            mContext.toast("Checkbox is $isChecked")
            if (isChecked) {
                holder.tv_content.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.tv_title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                Toast.makeText(mContext, "Selected!", Toast.LENGTH_SHORT).show()
            } else {
                holder.tv_content.paintFlags = 0
                holder.tv_title.paintFlags = 0
                Toast.makeText(mContext, "Not Selected!", Toast.LENGTH_SHORT).show()
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

    inner class TodoViewHolder(view: View?) {
        var tv_title = view!!.tv_todo_title
        var tv_content = view!!.tv_todo_content
        var checkBox = view!!.cb_todo_list

        fun bind(todo: TodoData) {
            tv_title?.text = todo.title
            tv_content?.text = todo.content
            checkBox?.isSelected = todo.checked
        }
    }


}


