package com.strange.yourdiary.data

data class TodoData(
    var id : Long,
    var title : String,
    var content : String,
    var checked: Boolean = false
) {
}