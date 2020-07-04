package com.strange.yourdiary.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
class TodoData(
    @PrimaryKey(autoGenerate = true) var id : Long,
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "content") var content : String,
    @ColumnInfo(name = "checked") var checked: Boolean = false,
    @ColumnInfo(name = "date") var date: String
) {
}