package com.strange.yourdiary.data

data class TodoData(
    var id : Long,
    var title : String,
    var Content : String,
    var checked: Boolean = false
) {
}