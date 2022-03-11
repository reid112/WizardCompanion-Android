package ca.rjreid.wizardcompanion.ui.screens.home

data class UiState(
    var resumeGameCardVisible: Boolean = false,
)

sealed class Action {
    data class Navigate(val route: String): Action()
}

sealed class UiEvent {
    object OnNewGameClicked: UiEvent()
    object OnResumeGameClicked: UiEvent()
}