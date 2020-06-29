package com.strange.yourdiary.data

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
class DiaryData(
    @PrimaryKey(autoGenerate = true) var id : Long,
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "content") var content : String,
    @ColumnInfo(name = "weather") @Nullable var weather : String,
    @ColumnInfo(name = "location") @Nullable var location : String,
    @ColumnInfo(name = "updateDate") var uploadDate : String
) {

}