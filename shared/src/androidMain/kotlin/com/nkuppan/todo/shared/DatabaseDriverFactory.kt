package com.nkuppan.todo.shared

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.nkuppan.todo.db.MyDatabase

/**
 * Created by ancientinc on 20/11/20.
 **/
actual class DatabaseDriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MyDatabase.Schema, context, "task.db")
    }
}