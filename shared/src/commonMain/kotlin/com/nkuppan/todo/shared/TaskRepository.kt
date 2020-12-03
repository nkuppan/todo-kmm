package com.nkuppan.todo.shared

import com.nkuppan.todo.db.MyDatabase
import com.nkuppan.todo.db.Task

open class TaskRepository(databaseDriverFactoryFactory: DatabaseDriverFactory) {

    private val database = MyDatabase(databaseDriverFactoryFactory.createDriver())

    private val dbQuery = database.taskQueries

    suspend fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllTask()
        }
    }

    suspend fun getAllTasks(): List<Task> {
        return dbQuery.findAllTask().executeAsList()
    }

    suspend fun findThisTask(aId: String): List<Task> {
        return dbQuery.findTaskById(aId).executeAsList()
    }

    suspend fun insertTask(aTask: Task) {
        dbQuery.insertTask(
            id = aTask.id,
            title = aTask.title,
            description = aTask.description,
            status = aTask.status,
            created_on = aTask.created_on,
            updated_on = aTask.updated_on
        )
    }
}