package com.strange.yourdiary.widget.listview.holder

import android.view.View
import com.strange.yourdiary.data.TodoData
import kotlinx.android.synthetic.main.lv_todo_list.view.*

class TodoViewHolder(view: View?) {
    var tv_title = view!!.tv_todo_title
    var tv_content = view!!.tv_todo_content
    var checkBox = view!!.cb_todo_list

    fun bind(todo: TodoData) {
        tv_title?.text = todo.title
        tv_content?.text = todo.content
        checkBox?.isSelected = todo.checked
    }
}