package com.strange.yourdiary.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.strange.yourdiary.R
import com.strange.yourdiary.ui.add.AddActivity
import com.strange.yourdiary.ui.main.fragment.CalendarFragment
import com.strange.yourdiary.ui.main.fragment.DiaryListFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100

    var REQUIRED_PERMISSIONS = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

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
                    val intent = Intent(this, AddActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    // intent 시 화면전환 효과 없애줌
                    finish()
                }
            }
            false
        }

        var permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        // 권한 체크
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            toast("권한 승인이 필요합니다.")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size) {
            toast("권한이 승인 되었습니다.")
        } else {
            toast("권한이 거부 되었습니다.")
        }
    }
}

