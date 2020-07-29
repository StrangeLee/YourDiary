package com.strange.yourdiary.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.db.AppDatabase
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    // Todo : exception handling (Ex - didn't write or choose any widget)
    private val calendar = Calendar.getInstance()

    private var db : AppDatabase? = null

    var curDate = calendar.get(Calendar.DATE).toString()
    var curDay = calendar.get(Calendar.DAY_OF_WEEK).toString()
    var curTime = SimpleDateFormat("HH:mm").format(calendar.time)

    // gps variable
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var geoCoder : Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar_add)

        // db instance 생성 및 insert
        db = AppDatabase.getInstance(this)

        tv_add_date.text = SimpleDateFormat("yyyy년 MM월 dd일 E").format(calendar.time).toString()

        // 위치 체크
        // get gps location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        var mAddress : StringBuffer = StringBuffer()
        geoCoder = Geocoder(this, Locale.KOREA)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                val addresses = geoCoder.getFromLocation(location!!.latitude, location.longitude, 1)
                for (addr: Address in addresses) {
                    val index = addr.maxAddressLineIndex
                    for(i : Int in arrayOf(index)){
                        mAddress.append(addr.getAddressLine(i));
                        mAddress.append(" ");
                    }
                    mAddress.append("\n");
                }

                Log.d("TEST", mAddress.toString())
            }
            .addOnFailureListener {exception: Exception ->
                exception.printStackTrace()
                Log.e("TEST", exception.message)
            }
    }

    // finish write diary's content
    fun writeDiary(view : View) {
        val diaryTitle = edit_add_title.text
        val diaryContent = edit_add_content.text

        if (TextUtils.isEmpty(diaryTitle) || diaryContent == null) {
            showDialog(getString(R.string.add_diary_null_exception))
        } else {
            showDialog(getString(R.string.add_diary_complete))
        }
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(ContextThemeWrapper(this,
            R.style.Theme_AppCompat_Dialog
        ))

        val addRunnable = Runnable {
            val diary = DiaryData(
                id = 0,
                title = edit_add_title.text.toString(),
                content = edit_add_content.text.toString(),
                weather = "맑음",
                location = "대구 광역시",
                uploadDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
            )

            db?.diaryDao()?.insert(diary)
        }

        if (message == getString(R.string.add_diary_null_exception)) {
            builder.run {
                setTitle("일기 저장")
                    .setMessage(message)
                    .setPositiveButton("확인") {_, _ ->  }
                    .show()
            }
        } else {
            builder.run {
                setTitle("일기 저장")
                    .setMessage(message)
                    .setPositiveButton("확인") { _, _ ->

                        val thread = Thread(addRunnable)
                        thread.start()

                        val intent = Intent(this@AddActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    .setNegativeButton("취소") { _, _ ->
                    }
                    .show()
            }
        }
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}
