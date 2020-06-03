package com.strange.yourdiary.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class DiaryData(
    var id : Long,
    var title : String,
    var content : String,
    var weather : String,
    var location : String,
    var uploadDate : String
) {

}