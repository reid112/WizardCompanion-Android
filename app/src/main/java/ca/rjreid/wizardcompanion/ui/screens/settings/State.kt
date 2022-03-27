package ca.rjreid.wizardcompanion.ui.screens.settings

import ca.rjreid.wizardcompanion.data.models.ThemeSetting

data class UiState(
    var theme: ThemeSetting = ThemeSetting.SYSTEM,
    var keepScreenOn: Boolean = true,
)