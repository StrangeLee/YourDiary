 package com.strange.yourdiary.ui.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.ui.DetailDialog
import com.strange.yourdiary.ui.main.DiaryAdapter
import kotlinx.android.synthetic.main.fragment_diary_list.*
import java.text.SimpleDateFormat
import java.util.*

 class DiaryListFragment : Fragment(),
     DiaryAdapter.OnDiaryListener {

     private var diaryList = ArrayList<DiaryData>()
     lateinit var diaryAdapter : DiaryAdapter
     var cal = Calendar.getInstance()

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_diary_list, container, false)

        // 정적 데이터 삽입
        initData()

        // 현재 월 textView
        tv_diary_month.text = SimpleDateFormat("MMM").format(cal.time)

        // adapter setting
        diaryAdapter = DiaryAdapter(
            context = view.context,
            diarys = diaryList,
            onDiaryListener = this
        )
        diaryAdapter.notifyDataSetChanged()

        rlv_diary.adapter = diaryAdapter
        rlv_diary.layoutManager = LinearLayoutManager(container!!.context)
        rlv_diary.setHasFixedSize(true)

        return view;
    }

     fun initData() {
         diaryList.add(
             DiaryData(
                 1,
                 "코딩 한날",
                 "코딩을 했다.\"열심히 했다.\n힘들었다.......",
                 "",
                 "",
                 SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.time)
             )
         )
         diaryList.add(
             DiaryData(
                 2,
                 "기능 한날",
                 "기능을 했다.\"열심히 했다.\n힘들었다.......",
                 "",
                 "",
                 SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.time)
             )
         )
         diaryList.add(
             DiaryData(
                 3,
                 "운동 한날",
                 "운동을 했다.\"열심히 했다.\n힘들었다.......",
                 "",
                 "",
                 SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.time)
             )
         )
     }

     // DiaryAdapter 의 OnDiaryClick 의 override 함수를
     override fun onDiaryClick(position: Int) {
         val dialog = DetailDialog(activity!!.applicationContext)
         dialog.show()
     }
}
