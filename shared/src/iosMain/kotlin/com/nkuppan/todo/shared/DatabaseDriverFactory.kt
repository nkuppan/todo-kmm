package com.nkuppan.todo.shared

import com.nkuppan.todo.db.MyDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

/**
 * Created by ancientinc on 20/11/20.
 **/
actual class DatabaseDriverFactory {

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MyDatabase.Schema, "task.db")
    }
}