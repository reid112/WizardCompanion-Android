package ca.rjreid.wizardcompanion

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ca.rjreid.wizardcompanion.data.WizardSettings
import ca.rjreid.wizardcompanion.data.models.ThemeSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WizardSettingsImpl(context: Context, name: String) : WizardSettings {
    //region Variables
    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = name)
    private val wizardDataStore = context.preferencesDataStore

    private val themeKey = intPreferencesKey("theme_test")
    private val keepScreenOnKey = booleanPreferencesKey("keep_screen_on")
    //endregion

    //region Public
    override fun getThemePref(): Flow<Int?> {
        return wizardDataStore.data.map { settings ->
            settings[themeKey]
        }
    }

    override suspend fun setThemePref(theme: ThemeSetting) {
        wizardDataStore.edit { settings ->
            settings[themeKey] = theme.value
        }
    }

    override fun getKeepScreenOnPref(): Flow<Boolean?> {
        return wizardDataStore.data.map { settings ->
            settings[keepScreenOnKey]
        }
    }

    override suspend fun setKeepScreenOnPref(keepScreenOn: Boolean) {
        wizardDataStore.edit { settings ->
            settings[keepScreenOnKey] = keepScreenOn
        }
    }
    //endregion
}