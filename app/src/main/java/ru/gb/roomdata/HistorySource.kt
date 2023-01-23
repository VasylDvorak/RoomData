package ru.gb.roomdata

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

private const val ID = "id"
private const val NAME = "name"
private const val YEAR = "year"
private const val ISLIKE = "isLike"
private const val CURRENTREQUEST = "current_request"

class HistorySource(
    private val contentResolver: ContentResolver // Работаем с Content Provider через этот класс
) {

    private var cursor: Cursor? = null

       fun query() {
        cursor = contentResolver.query(
            HISTORY_URI,
            null,
            null,
            null,
            null
        )
    }

    fun getHistory(): MutableList<HistoryEntity> {
        var historyList: MutableList<HistoryEntity> = mutableListOf()
        query()
            cursor?.let { cursor ->
            for (i in 0..cursor.count) {
                // Переходим на позицию в Cursor
                if (cursor.moveToPosition(i)) {
                    // Берём из Cursor строку
                    historyList.add(toEntity(cursor))
                }
            }
        }
        cursor?.close()
        return historyList
    }


    fun getMovieByPosition(position: Int): HistoryEntity {
        query()
        var historyEntity: HistoryEntity = if (cursor == null) {
            HistoryEntity()
        } else {
            cursor?.moveToPosition(position)
            toEntity(cursor!!)
        }
        cursor?.close()
        return historyEntity
    }


    fun insert(entity: HistoryEntity) {
        contentResolver.insert(HISTORY_URI, toContentValues(entity))
        query()
    }

    // Редактируем данные
    fun update(entity: HistoryEntity) {
        val uri: Uri = ContentUris.withAppendedId(HISTORY_URI, entity.id)
        contentResolver.update(uri, toContentValues(entity), null, null)
        query()
    }

    // Удалить запись в истории запросов
    fun delete(entity: HistoryEntity) {
        val uri: Uri = ContentUris.withAppendedId(HISTORY_URI, entity.id)
        contentResolver.delete(uri, null, null)
        query()
    }

    companion object {
        private val HISTORY_URI: Uri =
            Uri.parse("content://com.example.cinema.provider/HistoryEntity")
    }

    fun toContentValues(student: HistoryEntity): ContentValues {
        return ContentValues().apply {
            put(ID, student.id)
            put(NAME, student.name)
            put(YEAR, student.year)
            put(ISLIKE, student.isLike)
        }
    }

    @SuppressLint("Range")
    fun toEntity(cursor: Cursor): HistoryEntity {
        return HistoryEntity(
            cursor.getLong(cursor.getColumnIndex(ID)),
            cursor.getString(cursor.getColumnIndex(NAME)),
            cursor.getInt(cursor.getColumnIndex(YEAR)),
            cursor.getInt(cursor.getColumnIndex(ISLIKE)),
            cursor.getInt(cursor.getColumnIndex(CURRENTREQUEST))
        )
    }
}
