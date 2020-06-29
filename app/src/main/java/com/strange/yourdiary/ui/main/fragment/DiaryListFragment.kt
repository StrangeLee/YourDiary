 package com.strange.yourdiary.ui.main.fragment

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.db.AppDatabase
import com.strange.yourdiary.ui.dialog.DetailDialog
import com.strange.yourdiary.ui.main.DiaryAdapter
import kotlinx.android.synthetic.main.fragment_diary_list.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

 class DiaryListFragment : Fragment(),
     DiaryAdapter.OnDiaryListener {

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
        var view : View = inflater.inflate(R.layout.fragment_diary_list, container, false)

        // get db instance
        db = AppDatabase.getInstance(context!!)

        // 현재 월 textView
        view.tv_diary_month.text = SimpleDateFormat("MMM").format(cal.time)

        // get data form db

        var runnable = Runnable {
            try {
                diaryList = db?.diaryDao()?.getAll()!!
                // adapter setting
                activity!!.runOnUiThread {
                    diaryAdapter = DiaryAdapter(
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
         val dialog = DetailDialog(context!!)
         dialog.show()
         // Todo : 추가하기^^7
     }

     override fun onDestroy() {
         AppDatabase.destroyInstance()
         thread.interrupt()
         super.onDestroy()
     }
}
