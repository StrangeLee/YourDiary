 package com.strange.yourdiary.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.db.AppDatabase
import com.strange.yourdiary.widget.dialog.DetailDialog
import com.strange.yourdiary.widget.recyclerview.adapter.DiaryAdapter
import com.strange.yourdiary.widget.recyclerview.listener.OnDiaryListener
import kotlinx.android.synthetic.main.fragment_diary_list.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

 class DiaryListFragment : Fragment(),
     OnDiaryListener {

     private var diaryList = listOf<DiaryData>()
     lateinit var diaryAdapter : DiaryAdapter
     var cal = Calendar.getInstance()
     lateinit var thread : Thread

     // room db 생성
     private var db : AppDatabase? = null

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_diary_list, container, false)

        // get db instance
        db = AppDatabase.getInstance(context!!)

        // 현재 월 textView
        view.tv_diary_month.text = SimpleDateFormat("MMM").format(cal.time)

        // get data form db

        val runnable = Runnable {
            try {
                diaryList = db?.diaryDao()?.getAll()!!
                // adapter setting
                activity!!.runOnUiThread {
                    diaryAdapter =
                        DiaryAdapter(
                            context = context!!,
                            diarys = diaryList,
                            onDiaryListener = this
                        )

                    diaryAdapter.notifyDataSetChanged()
                    view.rlv_diary.adapter = diaryAdapter
                    view.rlv_diary.layoutManager = LinearLayoutManager(container!!.context)
                    view.rlv_diary.setHasFixedSize(true)
                }
            } catch (e : Exception) {
                Log.d("DiaryList", "Error - $e")
            }
        }

        thread = Thread(runnable)
        thread.start()

        return view;
    }

     // DiaryAdapter 의 OnDiaryClick 의 override 함수를
     override fun onDiaryClick(position: Int) {
         val dialog = DetailDialog(context!!, diaryList[position])
         Log.d("Data", diaryList[position].content)
         dialog.show()
     }

     override fun onDestroy() {
         AppDatabase.destroyInstance()
         thread.interrupt()
         super.onDestroy()
     }
}
