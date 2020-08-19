package com.strange.yourdiary.data

import androidx.appcompat.app.AppCompatActivity
import com.strange.yourdiary.R

class EmotionData(private val emotion : String) : AppCompatActivity() {

    fun getEmotion(): Int {
        when (emotion) {
            getString(R.string.emotion_happy) -> return R.drawable.icon_happy_emotion
            getString(R.string.emotion_sad) -> return R.drawable.icon_sad_emotion
            getString(R.string.emotion_tired) -> return R.drawable.icon_tired_emotion
            getString(R.string.emotion_angry) -> return R.drawable.icon_angry_emotion
            getString(R.string.emotion_soso) -> return R.drawable.icon_soso_emotion
        }

        return 0
    }

}