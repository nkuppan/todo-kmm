package com.nkuppan.todo.shared

import android.content.Context
import com.nkuppan.todo.db.MyDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by ancientinc on 20/11/20.
 **/
actual class DatabaseDriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MyDatabase.Schema, context, "task.db")
    }
}