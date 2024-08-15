package com.bunli.tts.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.bunli.tts.model.Order

class MyContentProvider : ContentProvider() {

    companion object {
        const val COL_ID = "id"
        const val COL_CODE = "code"
        const val AUTHORITIES = "com.bunli.tts.provider.MyContentProvider"
        const val TABLE_NAME = "orders"
        val CONTENT_URI = Uri.parse("content://$AUTHORITIES/$TABLE_NAME")
    }

    private val ORDER = 1
    private val ORDER_ID = 2
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, TABLE_NAME, ORDER)
        addURI(AUTHORITIES, "$TABLE_NAME/#", ORDER_ID)
    }

    private val data = mutableListOf(
        Order(1, "BBS"),
        Order(2, "Express"),
        Order(3, "Brand"),
        Order(4, "HVC"),
        Order(5, "High value"),
    )

    override fun onCreate(): Boolean {
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when (sUriMatcher.match(uri)) {
            ORDER_ID -> {
                val id = uri.pathSegments[1].toInt()
                val remove = data.removeIf { it.id == id }
                return if (remove) id else -1
            }

            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (sUriMatcher.match(uri)) {
            ORDER -> {
                val newOrder =
                    Order(data.maxBy { it.id }.id + 1, values?.get(COL_CODE)?.toString() ?: "")
                data.add(newOrder)
                return Uri.parse("content://$AUTHORITIES/$TABLE_NAME/${newOrder.id}")
            }

            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor? {
        when (sUriMatcher.match(uri)) {
            ORDER -> {
                val columns = projection ?: arrayOf(COL_ID, COL_CODE)
                val cursor = MatrixCursor(columns)
                data.forEach { item ->
                    val values = mutableListOf<Any>()
                    columns.forEach {
                        when (it) {
                            COL_ID -> values.add(item.id)
                            COL_CODE -> values.add(item.code)
                        }
                    }
                    cursor.addRow(values)
                }
                return cursor
            }

            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?,
    ): Int {
        when (sUriMatcher.match(uri)) {
            ORDER_ID -> {
                val updateItem = data.firstOrNull { it.id == uri.pathSegments[1].toInt() }?.apply {
                    code = values?.get(COL_CODE)?.toString() ?: ""
                }
                return updateItem?.id ?: -1
            }

            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }
}