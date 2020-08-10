package com.strange.yourdiary.widget.recyclerview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.R
import com.strange.yourdiary.widget.recyclerview.holder.DiaryViewHolder
import com.strange.yourdiary.widget.recyclerview.listener.OnDiaryListener
import kotlinx.android.synthetic.main.rlv_diary_list.view.*

class DiaryAdapter(val context : Context, val diarys : List<DiaryData>, val onDiaryListener: OnDiaryListener) :
    RecyclerView.Adapter<DiaryViewHolder>() {

    private var mOnDiaryListener : OnDiaryListener = onDiaryListener

    override fun onCreateViewHolder(parent: ViewGroup, positon: Int): DiaryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rlv_diary_list, parent, false)

        return DiaryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diarys.size
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, positon: Int) {
        holder.bind(diarys[positon], mOnDiaryListener)
    }
}