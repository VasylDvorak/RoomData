package ru.gb.roomdata

data class HistoryEntity (
    var id: Long = 0,
    var name: String = "",
    var year: Int = 0,
    var isLike: Int = 0,
    var current_request: Int = 0
)