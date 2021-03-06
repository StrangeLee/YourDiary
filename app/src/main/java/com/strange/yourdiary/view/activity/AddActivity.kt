package com.strange.yourdiary.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.databinding.DataBindingUtil
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.strange.yourdiary.R
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.databinding.ActivityAddBinding
import com.strange.yourdiary.databinding.DialogDiaryDetailBinding
import com.strange.yourdiary.db.AppDatabase
import com.strange.yourdiary.service.WeatherRetrofit
import com.strange.yourdiary.viewmodel.DiaryViewModel
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.view.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

// 2020-08-24 Todo : code 정리하기
class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var diaryViewModel : DiaryViewModel

    private val calendar = Calendar.getInstance()

    // gps variable
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var geoCoder : Geocoder
    private lateinit var locationManager : LocationManager

    private lateinit var nowLocationString: String
    private lateinit var nowWeatherString: String
    private lateinit var mLocation : Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel::class.java)

        setSupportActionBar(toolbar_add)

        binding.root.tv_add_date.text = SimpleDateFormat("yyyy년 MM월 dd일 E").format(calendar.time).toString()

        checkGpsEnabled()
    }

    // 예외처리 dialog
    fun beforeSaveDiary(view : View) {
        val diaryTitle = edit_add_title.text
        val diaryContent = edit_add_content.text

        if (TextUtils.isEmpty(diaryTitle) || diaryContent == null) {
            showDialog(getString(R.string.add_diary_null_exception))
        } else {
            showDialog(getString(R.string.add_diary_complete))
        }
    }

    // 일기 저장을 눌렀을 때 예외 처리후 보여주는 다이얼로그
    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(ContextThemeWrapper(this,
            R.style.Theme_AppCompat_Dialog
        ))

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
                        saveDiary()

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

    // room db 에 Diary 객체 저장
    private fun saveDiary() {
        diaryViewModel.insert(DiaryData(
            id = 0,
            title = edit_add_title.text.toString(),
            content = edit_add_content.text.toString(),
            weather = nowWeatherString,
            location = nowLocationString,
            uploadDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
        ))
    }

    // 현재위치 가져오기
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission( // 위치 권한 체크
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val mAddress = StringBuffer()
        geoCoder = Geocoder(this, Locale.KOREA)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                val addresses = geoCoder.getFromLocation(location!!.latitude, location.longitude, 1)
                mLocation = location
                /**
                 * Address class 변수 정리 (필요한 것들만 정리함)
                 * adminArea : 지역, 시 이름 ..ex) 대구광역시
                 * thoroughfare : 상세 주소 ..ex) 평리3동
                 * postalCode : 우편주소(미국식 으로 기제된 듯 하다. 한국식으로 변환 하는 방법은 찾아야할듯) ..ex) 703-013
                 * countryCode : 나라 코드 ..ex) KR, UK etc...
                 * countryName : 나라 이름 ..ex) 대한민국
                 * locality : 일반 집주소나 회사 주소같은 경우는 null로 뜨는 듯 하다. 아마 산지 인지 바다 인지 등을 나타내는 거 같은데 확인 필요.
                 */
                for (addr: Address in addresses) {
                    val index = addr.maxAddressLineIndex
                    for(i : Int in arrayOf(index)){
                        mAddress.append(addr.getAddressLine(i))
                        mAddress.append(" ")
                    }
                    mAddress.append("\n")

                    nowLocationString = "${addr.adminArea} ${addr.thoroughfare}"

                    Log.d("Test", addr.toString())
                }

                getWeatherFromApi()
            }
            .addOnFailureListener {exception: Exception ->
                // 2020/08/11 Todo : 오류 발생시 다이얼로그 뛰우기 or toast
                nowLocationString = "주소를 가져오지 못했습니다."
                Log.e("Error", exception.message)
                exception.printStackTrace()
            }
    }

    private fun getWeatherFromApi() {
        val response = WeatherRetrofit.getService()!!.getCurrentWeather(
            mLocation.latitude.toString(),
            mLocation.longitude.toString(),
            "4fee01e748ecda78c741ad4d607fec49"
        )

        response.enqueue(object: Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error", t.message)
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.d("Weather", response.body().toString())
                val jsonObject = JSONObject(response.body().toString())
                val jsonArray = jsonObject.getJSONArray("weather")

                val mainInfo = jsonObject.getJSONObject("main")
                Log.d("Weather", (mainInfo.getString("temp").toFloat() - 273).toInt().toString())
                nowWeatherString = (mainInfo.getString("temp").toFloat() - 273).toInt().toString() + "º, "

//                for (i in 0 until jsonArray.length()) {
//                    val obj = jsonArray.getJSONObject(i)
//                    Log.d("Weather", obj.getString("main"))
//                    Log.d("Weather", obj.getString("description"))
//                    nowWeatherString = getWeatherToString(obj.getString("main"))
//                }
            }
        })
    }

    private fun checkGpsEnabled() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            toast("날씨를 불러오기 위해서 GPS를 켜주셔야합니다.")
            val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {
            // get gps location
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            getCurrentLocation()
        }
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }

}


