package com.strange.yourdiary.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.R
import kotlinx.android.synthetic.main.rlv_diary_list.view.*

class DiaryAdapter(val context : Context, val diarys : List<DiaryData>, val onDiaryListener: OnDiaryListener) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

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

    inner class DiaryViewHolder(view: View?) : RecyclerView.ViewHolder(view!!), View.OnClickListener {

        private var tvDate = itemView.tv_diary_date
        private var tvDay = itemView.tv_diary_day
        private var tvTime = itemView.tv_diary_time
        private var tvTitle = itemView.tv_diary_title

        private lateinit var onDiaryListener : OnDiaryListener

        fun bind(diary: DiaryData, onDiaryListener : OnDiaryListener) {
            tvDate?.text = diary.uploadDate.substring(8, 10)
            tvDay?.text = getMonth(diary.uploadDate.substring(5, 7).toInt())
            tvTime?.text = diary.uploadDate.substring(11, diary.uploadDate.length)
            tvTitle?.text = diary.title
            this.onDiaryListener = onDiaryListener

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onDiaryListener.onDiaryClick(adapterPosition)
        }
    }

    public interface OnDiaryListener {
        fun onDiaryClick(position : Int)
    }

    private fun getMonth(month : Int) : String {
        when (month) {
            1 -> return "Jan"
            2 -> return "Feb"
            3 -> return "Mar"
            4 -> return "Apr"
            5 -> return "May"
            6 -> return "Jun"
            7 -> return "Jul"
            8 -> return "Aug"
            9 -> return "Sept"
            10 -> return "Oct"
            11 -> return "Nov"
            12 -> return "Dec"
        }

        return "Value"
    }
}