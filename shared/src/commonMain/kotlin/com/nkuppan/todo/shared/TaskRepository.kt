package com.nkuppan.todo.shared

import com.nkuppan.todo.db.MyDatabase
import com.nkuppan.todo.db.Task

open class TaskRepository(databaseDriverFactoryFactory: DatabaseDriverFactory) {

    private val database = MyDatabase(databaseDriverFactoryFactory.createDriver())

    private val taskDBQuery = database.taskQueries
    private val subTaskDBQuery = database.subTaskQueries
    private val tagsDBQuery = database.tagsQueries

    suspend fun clearDatabase() {
        taskDBQuery.transaction {
            taskDBQuery.removeAllTask()
        }
    }

    suspend fun getAllTasks(): List<Task> {
        return taskDBQuery.findAllTask().executeAsList()
    }

    suspend fun findThisTask(aId: String): Task? {

        val task = taskDBQuery.findTaskById(aId).executeAsOneOrNull()

        if (task != null) {
            //TODO read tags and sub tasks
            //val tagsList = tagsDBQuery.findTagById().executeAsList();
        }

        return task
    }

    suspend fun insertTask(aTask: Task) {
        taskDBQuery.insertTask(
            id = aTask.id,
            title = aTask.title,
            description = aTask.description,
            status = aTask.status,
            created_on = aTask.created_on,
            updated_on = aTask.updated_on
        )
    }
}