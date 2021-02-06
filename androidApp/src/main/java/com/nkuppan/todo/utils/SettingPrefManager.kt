package com.nkuppan.todo.utils

import android.content.Context

object SettingPrefManager {

    fun initialize(aContext: Context) {
        SecuredPreferenceManager.initialize(aContext, SettingPrefKeys.PREF_NAME)
    }

    fun storeSelectedTaskGroup(aTaskGroupId: String) {
        SecuredPreferenceManager.storeValue(SettingPrefKeys.SELECTED_TASK_GROUP, aTaskGroupId)
    }

    fun getSelectedTaskGroup(): String {
        return SecuredPreferenceManager.getStringValue(SettingPrefKeys.SELECTED_TASK_GROUP) ?: "1"
    }

    fun storeFilterOption(aFilterType: Int) {
        SecuredPreferenceManager.storeValue(SettingPrefKeys.SELECTED_FILTER, aFilterType)
    }

    fun getFilterType(): Int {
        return SecuredPreferenceManager.getIntValue(SettingPrefKeys.SELECTED_FILTER) ?: 0
    }

    fun storeThemeType(aThemeType: Int) {
        SecuredPreferenceManager.storeValue(SettingPrefKeys.THEME_TYPE, aThemeType)
    }

    fun getThemeType(): Int {
        return SecuredPreferenceManager.getIntValue(SettingPrefKeys.THEME_TYPE) ?: 0
    }

    fun storeCompletedTaskOpenedStatus(isOpened: Boolean) {
        SecuredPreferenceManager.storeValue(SettingPrefKeys.SELECTED_FILTER, isOpened)
    }

    fun isCompletedTaskOpened(): Boolean {
        return SecuredPreferenceManager.getBooleanValue(SettingPrefKeys.COMPLETED_TASK_OPENED)
            ?: false
    }
}

object SettingPrefKeys {

    const val PREF_NAME = "settings_pref"

    const val THEME_TYPE = "theme_type"

    const val SELECTED_TASK_GROUP = "selected_task_group"
    const val SELECTED_FILTER = "selected_filter"

    const val COMPLETED_TASK_OPENED = "completed_task_opened"
}