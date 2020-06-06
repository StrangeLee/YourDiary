package com.strange.yourdiary.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.strange.yourdiary.R
import com.strange.yourdiary.ui.add.AddActivity
import com.strange.yourdiary.ui.main.fragment.CalendarFragment
import com.strange.yourdiary.ui.main.fragment.DiaryListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        // ActionBar Setting
        val ab = supportActionBar!!
        ab.setDisplayShowCustomEnabled(false)

        // fragment 전환 및 세팅
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame,
                DiaryListFragment()
            )
            .commit()

        btn_calendar.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame,
                    CalendarFragment()
                )
                .commit()
        }

        btn_diary.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame,
                    DiaryListFragment()
                )
                .commit()
        }


        // BottomNavigation
        bnv_diary.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_add -> {
                    var intent = Intent(this, AddActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0) // intent 시 화면전환 효과 없애줌
                    finish()
                }
            }
            false
        }
    }
}

