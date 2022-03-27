package ca.rjreid.wizardcompanion.data

import ca.rjreid.wizardcompanion.data.models.ThemeSetting
import kotlinx.coroutines.flow.Flow

interface WizardSettings {
    //region Theme
    fun getThemePref(): Flow<Int?>
    suspend fun setThemePref(theme: ThemeSetting)
    //endregion

    //region Keep Screen On
    fun getKeepScreenOnPref(): Flow<Boolean?>
    suspend fun setKeepScreenOnPref(keepScreenOn: Boolean)
    //endregion
}