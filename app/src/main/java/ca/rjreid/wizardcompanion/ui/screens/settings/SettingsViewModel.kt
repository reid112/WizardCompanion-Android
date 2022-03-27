package ca.rjreid.wizardcompanion.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.data.models.ThemeSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val repository: WizardRepository
) : ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set
    //endregion

    //region Init
    init {
        getSettings()
    }
    //endregion

    //region Public
    fun toggleTheme() {
        viewModelScope.launch {
            val currentValue = uiState.theme.value
            var nextValue = 0

            if (currentValue < 3) {
                nextValue = currentValue + 1
            } else {
                nextValue = 1
            }

            repository.setThemeSetting(ThemeSetting.fromInt(nextValue) ?: ThemeSetting.SYSTEM)
        }
    }

    fun toggleScreen() {
        viewModelScope.launch {
            repository.setKeepScreenOnSetting(!uiState.keepScreenOn)
        }
    }
    //endregion

    //region Helpers
    private fun getSettings() {
        viewModelScope.launch {
            combine(repository.getThemeSetting(), repository.getKeepScreenOnSetting()) {
                    theme, keepScreenOn -> Pair(theme, keepScreenOn)
            }.collect { (theme, keepScreenOn) ->
                uiState = uiState.copy(
                    theme = theme,
                    keepScreenOn = keepScreenOn
                )
            }
        }
    }
    //endregion
}