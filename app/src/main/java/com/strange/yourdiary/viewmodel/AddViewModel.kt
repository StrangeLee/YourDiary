package com.strange.yourdiary.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.gson.JsonObject
import com.strange.yourdiary.data.DiaryData
import com.strange.yourdiary.repo.DiaryRepository
import com.strange.yourdiary.service.WeatherRetrofit
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = DiaryRepository(application)

    private lateinit var geoCoder : Geocoder
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var mLocation : Location

    private lateinit var nowLocationString: String
    private lateinit var nowWeatherString: String

    fun insert(diary : DiaryData) {
        repository.insert(diary)
    }

    // 현재위치 가져오기
    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission( // 위치 권한 체크
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val mAddress = StringBuffer()
        geoCoder = Geocoder(getApplication(), Locale.KOREA)

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
                    getWeatherFromApi()
                }
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

        response.enqueue(object: Callback<JsonObject> {
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

    fun getWeather() : String {
        return nowWeatherString
    }

    fun getLocation() : String {
        return nowLocationString
    }

}