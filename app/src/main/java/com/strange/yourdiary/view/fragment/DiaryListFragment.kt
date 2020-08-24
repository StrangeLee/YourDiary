 package com.strange.yourdiary.view.fragment

import android.graphics.Point
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.databinding.FragmentDiaryListBinding
import com.strange.yourdiary.viewmodel.DiaryViewModel
import com.strange.yourdiary.widget.dialog.DetailDialog
import com.strange.yourdiary.widget.recyclerview.adapter.DiaryAdapter
import com.strange.yourdiary.widget.recyclerview.listener.OnDiaryListener
import kotlinx.android.synthetic.main.fragment_diary_list.view.*
import java.text.SimpleDateFormat

import java.util.*

 class DiaryListFragment : Fragment(), OnDiaryListener {

     private lateinit var binding : FragmentDiaryListBinding
     private lateinit var diaryViewModel : DiaryViewModel

     private var diaryList = listOf<DiaryData>()
     lateinit var diaryAdapter : DiaryAdapter
     var now = Calendar.getInstance()

//     lateinit var dialog : DetailDialog

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_list, container, false)
        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel::class.java)

        diaryViewModel.getAll().observe(this, Observer {
            diaryList = it
            diaryAdapter =
                DiaryAdapter(
                    context = context!!,
                    diarys = it,
                    onDiaryListener = this
                )

            binding.root.rlv_diary.adapter = diaryAdapter
            binding.root.rlv_diary.layoutManager = LinearLayoutManager(container!!.context)
            diaryAdapter.notifyDataSetChanged()
            binding.root.rlv_diary.setHasFixedSize(true)
        })

        binding.root.tv_diary_month.text = SimpleDateFormat("MMM.").format(now.time)

        return binding.root
    }

     // DiaryAdapter 의 OnDiaryClick 의 override 함수를
     override fun onDiaryClick(position: Int) {
         Log.d("Position", "position $position")
         val dialog = DetailDialog(context!!, diaryList[position])
         Log.d("Data", diaryList[position].content)
         dialog.show()

         // custom dialog size 지정
         val display = activity!!.windowManager.defaultDisplay
         val size = Point()

         display.getSize(size)

         val window = dialog.window

         val width : Int = (size.x * 0.85f).toInt()
         val height : Int = (size.y * 0.75f).toInt()

         window.setLayout(width, height)
     }
 }
