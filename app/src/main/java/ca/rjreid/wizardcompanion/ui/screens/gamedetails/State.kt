package ca.rjreid.wizardcompanion.ui.screens.gamedetails

import ca.rjreid.wizardcompanion.domain.models.Game

data class UiState(
    var loading: Boolean = true,
    var game: Game? = null,
    var error: Boolean = false,
)