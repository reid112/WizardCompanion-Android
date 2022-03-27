package ca.rjreid.wizardcompanion.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.util.RULES_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val repository: WizardRepository
) : ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set

    private val _actions = Channel<Action>()
    val actions = _actions.receiveAsFlow()
    //endregion

    //region Init
    init {
        getSettings()
    }
    //endregion

    //region Public
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnChangeKeepScreenOn -> {
                viewModelScope.launch {
                    repository.setKeepScreenOnSetting(event.value)
                }
            }
            is UiEvent.OnChangeTheme -> {
                viewModelScope.launch {
                    repository.setThemeSetting(event.theme)
                }
            }
            is UiEvent.OnThemeRowClicked -> {
                uiState = uiState.copy(themeSelectionDialogVisible = true)
            }
            is UiEvent.OnDismissThemeSelection -> {
                uiState = uiState.copy(themeSelectionDialogVisible = false)
            }
            is UiEvent.OnRulesRowClicked -> {
                viewModelScope.launch {
                    _actions.send(Action.NavigateToExternalUrl(RULES_URL))
                }
            }
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