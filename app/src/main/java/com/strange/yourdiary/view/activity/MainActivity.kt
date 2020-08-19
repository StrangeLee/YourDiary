package com.strange.yourdiary.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.Toast
import com.strange.yourdiary.R
import com.strange.yourdiary.view.fragment.CalendarFragment
import com.strange.yourdiary.view.fragment.DiaryListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var REQUIRED_PERMISSIONS = arrayOf(
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

        // toolbar 버튼 clickListener
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

        checkRunTimePermission()
    }

    // 위치 권한 확인
    private fun checkRunTimePermission() {
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (hasFineLocationPermission == PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PERMISSION_GRANTED
        ) {
            return
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    REQUIRED_PERMISSIONS.get(0)
                )
            ) {
                Toast.makeText(this@MainActivity, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG)
                    .show()
                ActivityCompat.requestPermissions(
                    this@MainActivity, REQUIRED_PERMISSIONS,
                    1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity, REQUIRED_PERMISSIONS,
                    1
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && permissions.size == REQUIRED_PERMISSIONS.size) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    REQUIRED_PERMISSIONS[0]
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    REQUIRED_PERMISSIONS[1]
                )
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

