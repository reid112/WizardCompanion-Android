package ca.rjreid.wizardcompanion.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.domain.mappers.toGame
import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.util.Routes
import ca.rjreid.wizardcompanion.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WizardRepository
): ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set

    private val _actions = Channel<Action>()
    val actions = _actions.receiveAsFlow()

    private var inProgressGame: Game? = null
    //endregion

    //region Init
    init {
        getInProgressGame()
    }
    //endregion

    //region Public
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnNewGameClicked -> {
                if (inProgressGame != null) {
                    uiState = uiState.copy(startGameDialogVisible = false)
                } else {
                    sendAction(Action.Navigate(Routes.ENTER_PLAYERS.route))
                }
            }
            is UiEvent.OnResumeGameClicked -> {
                sendAction(Action.Navigate("${Routes.SCORE.route}/${inProgressGame?.id ?: 0}"))
            }
            is UiEvent.OnStartGameDialogCancel -> {
                uiState = uiState.copy(startGameDialogVisible = false)
            }
            is UiEvent.OnStartGameDialogConfirm -> {
                uiState = uiState.copy(startGameDialogVisible = false)
                sendAction(Action.Navigate(Routes.ENTER_PLAYERS.route))
            }
        }
    }
    //endregion

    //region Helpers
    private fun getInProgressGame() {
        viewModelScope.launch {
            repository.getInProgressGame().collect { game ->
                inProgressGame = game?.toGame()
                uiState = uiState.copy(resumeGameCardVisible = game != null)
            }
        }
    }

    private fun sendAction(action: Action) {
        viewModelScope.launch {
            _actions.send(action)
        }
    }
    //endregion
}