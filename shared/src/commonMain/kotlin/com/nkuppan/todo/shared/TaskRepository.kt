package com.nkuppan.todo.shared

import com.nkuppan.todo.db.MyDatabase
import com.nkuppan.todo.db.SubTask
import com.nkuppan.todo.db.Task
import com.nkuppan.todo.db.TaskGroup
import com.nkuppan.todo.shared.utils.CommonUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class TaskRepository(databaseDriverFactoryFactory: DatabaseDriverFactory) {

    private val database = MyDatabase(databaseDriverFactoryFactory.createDriver())

    private val taskQuery = database.taskQueries
    private val subTaskQuery = database.subTaskQueries
    private val tagsQuery = database.tagsQueries
    private val taskAndTagsQuery = database.taskAndTagsQueries
    private val taskGroupQuery = database.taskGroupQueries

    init {
        CoroutineScope(Dispatchers.Default).launch {
            insertBasicGroups()
        }
    }

    private fun insertBasicGroups() {

        val taskGroupList = taskGroupQuery.findAllGroup().executeAsList()

        if (taskGroupList.isNullOrEmpty()) {
            insertTaskGroup(
                TaskGroup(
                    "1",
                    "My List",
                    CommonUtils.getDateTime().toDouble(),
                    CommonUtils.getDateTime().toDouble()
                )
            )
        }
    }

    fun clearDatabase() {
        taskQuery.transaction {
            taskQuery.removeAllTask()
            subTaskQuery.removeAllSubTask()
            tagsQuery.removeAllTags()
            taskAndTagsQuery.removeAll()
            taskGroupQuery.removeAll()
        }
    }

    fun getAllTasks(aGroupId: String): List<Task> {
        return taskQuery.findAllTask(aGroupId).executeAsList()
    }

    fun getPendingTask(aGroupId: String): List<Task> {
        return taskQuery.findPendingTask(aGroupId).executeAsList()
    }

    fun getCompletedTask(aGroupId: String): List<Task> {
        return taskQuery.findCompletedTask(aGroupId).executeAsList()
    }

    fun removeAllTasks(aTaskGroupId: String) {
        taskQuery.removeAllTasks(aTaskGroupId)
    }

    fun removeCompletedTask(aTaskGroupId: String) {
        taskQuery.removeCompletedTasks(aTaskGroupId)
    }

    fun findThisTask(aId: String): Task? {

        val task = taskQuery.findTaskById(aId).executeAsOneOrNull()

        if (task != null) {
            //TODO read tags and sub tasks
            //val tagsList = tagsDBQuery.findTagById().executeAsList();
        }

        return task
    }

    fun insertTask(aTask: Task) {
        taskQuery.insertTask(
            id = aTask.id,
            group_id = aTask.group_id,
            title = aTask.title,
            description = aTask.description,
            status = aTask.status,
            task_end_date = aTask.task_end_date,
            created_on = aTask.created_on,
            updated_on = aTask.updated_on
        )
    }

    fun findGroupById(aTaskGroupId: String): TaskGroup? {
        return taskGroupQuery.findGroupById(aTaskGroupId).executeAsOneOrNull()
    }

    fun findAllGroups(): List<TaskGroup> {
        return taskGroupQuery.findAllGroup().executeAsList()
    }

    fun removeThisGroup(aTaskGroupId: String) {
        taskGroupQuery.removeGroup(aTaskGroupId)
    }

    fun insertTaskGroup(aTaskGroup: TaskGroup) {
        taskGroupQuery.insertGroup(
            id = aTaskGroup.id,
            group_name = aTaskGroup.group_name,
            created_on = aTaskGroup.created_on,
            updated_on = aTaskGroup.updated_on
        )
    }

    fun revertThisTask(aTask: Task) {
        val newTask = getModifiedTask(aTask, 1)
        insertTask(newTask)
    }

    fun markThisTaskAsCompleted(aTask: Task) {
        val newTask = getModifiedTask(aTask, 2)
        insertTask(newTask)
    }

    private fun getModifiedTask(aTask: Task, aStatus: Long, aTaskGroupId: String? = null) = Task(
        aTask.id,
        if (aTaskGroupId.isNullOrEmpty()) aTask.group_id else aTaskGroupId,
        aTask.title,
        aTask.description,
        status = aStatus,
        task_end_date = aTask.task_end_date,
        created_on = aTask.created_on,
        updated_on = CommonUtils.getDateTime().toDouble()
    )

    fun removeTask(aTask: Task) {
        subTaskQuery.removeThisSubTask(aTask.id)
        taskQuery.removeTask(aTask.id)
    }

    fun updateTaskGroup(aTask: Task, aTaskGroup: TaskGroup) {
        val modifiedTask = getModifiedTask(aTask, aTask.status, aTaskGroup.id)
        insertTask(modifiedTask)
    }

    fun getSubTaskList(aTaskId: String): List<SubTask> {
        return subTaskQuery.findAllSubTask(aTaskId).executeAsList()
    }
}