package ca.rjreid.wizardcompanion.ui.screens.settings

import ca.rjreid.wizardcompanion.data.models.ThemeSetting

data class UiState(
    var themeSelectionDialogVisible: Boolean = false,
    var theme: ThemeSetting = ThemeSetting.SYSTEM,
    var keepScreenOn: Boolean = true,
)

sealed class UiEvent {
    object OnThemeRowClicked: UiEvent()
    object OnDismissThemeSelection: UiEvent()
    data class OnChangeKeepScreenOn(val value: Boolean): UiEvent()
    data class OnChangeTheme(val theme: ThemeSetting): UiEvent()
}