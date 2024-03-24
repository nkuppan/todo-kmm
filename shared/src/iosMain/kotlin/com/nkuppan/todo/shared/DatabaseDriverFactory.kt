package com.nkuppan.todo.shared

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.nkuppan.todo.db.MyDatabase

/**
 * Created by ancientinc on 20/11/20.
 **/
actual class DatabaseDriverFactory {

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MyDatabase.Schema, "task.db")
    }
}