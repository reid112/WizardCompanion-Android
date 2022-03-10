package ca.rjreid.wizardcompanion.ui.screens.home

data class UiState(
    var resumeGameCardVisible: Boolean = false,
)

sealed class Action {
    data class Navigate(val route: String): Action()
    data class ShowBottomSheet(val sheet: BottomSheetType): Action()
}

sealed class UiEvent {
    object OnNewGameClicked: UiEvent()
    object OnResumeGameClicked: UiEvent()
}

sealed class BottomSheetType {
    object StartNewGame: BottomSheetType()
}