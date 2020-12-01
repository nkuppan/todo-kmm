package com.nkuppan.todo.shared

import com.nkuppan.todo.db.MyDatabase
import com.nkuppan.todo.db.Task

open class TaskRepository(databaseDriverFactoryFactory: DatabaseDriverFactory) {

    private val database = MyDatabase(databaseDriverFactoryFactory.createDriver())

    private val dbQuery = database.taskQueries

    fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllTask()
        }
    }

    fun getAllTasks(): List<Task> {
        return dbQuery.findAllTask().executeAsList()
    }

    fun findThisTask(aId: String): List<Task> {
        return dbQuery.findTaskById(aId).executeAsList()
    }

    fun insertTask(aTask: Task) {
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