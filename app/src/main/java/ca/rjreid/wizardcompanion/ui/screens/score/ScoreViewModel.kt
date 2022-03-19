package ca.rjreid.wizardcompanion.ui.screens.score

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rjreid.wizardcompanion.data.ScoreManager
import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Round
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val scoreManager: ScoreManager
): ViewModel() {
    //region Variables
    private lateinit var game: Game
    private lateinit var currentRound: Round
    private var hasDealt = false

    var uiState by mutableStateOf(UiState())
        private set

    private val _actions = Channel<Action>()
    val actions = _actions.receiveAsFlow()
    //endregion

    //region Init
    init {
        viewModelScope.launch {
            scoreManager.getGameDetails().collect {
                game = it
                currentRound = game.rounds.last()

                val currentDealerIndex = currentRound.playerBids.indexOfFirst { playerBid -> playerBid.player == currentRound.dealer }
                val rotate = currentRound.playerBids.size - currentDealerIndex - 1

                Collections.rotate(currentRound.playerBids, rotate)

                val isLastRound = currentRound.number == scoreManager.getTotalRounds(game)

                uiState = uiState.copy(
                    forceUpdate = UUID.randomUUID().toString(),
                    hasDealt = hasDealt,
                    roundNumber = currentRound.number,
                    dealer = currentRound.dealer.name,
                    bids = currentRound.playerBids,
                    isLastRound = isLastRound,
                    winner = game.winner,
                    gameSummary = game
                )
            }
        }
    }
    //endregion

    //region Public
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnAddBidClicked -> {
                val currentBid = currentRound.playerBids.first { it.id == event.bid.id }.bid
                currentRound.playerBids.first { it.id == event.bid.id }.bid = currentBid + 1
                updatePlayerBidsUiState()
            }
            is UiEvent.OnAddActualClicked -> {
                val currentActual = currentRound.playerBids.first { it.id == event.bid.id }.actual
                currentRound.playerBids.first { it.id == event.bid.id }.actual = currentActual + 1
                updatePlayerBidsUiState()
            }
            is UiEvent.OnRemoveBidClicked -> {
                val currentBid = currentRound.playerBids.first { it.id == event.bid.id }.bid
                currentRound.playerBids.first { it.id == event.bid.id }.bid = currentBid - 1
                updatePlayerBidsUiState()
            }
            is UiEvent.OnRemoveActualClicked -> {
                val currentActual = currentRound.playerBids.first { it.id == event.bid.id }.actual
                currentRound.playerBids.first { it.id == event.bid.id }.actual = currentActual - 1
                updatePlayerBidsUiState()
            }
            is UiEvent.OnDealClicked -> {
                hasDealt = true
                viewModelScope.launch {
                    scoreManager.updateGame(game)
                }
            }
            is UiEvent.OnNextRoundClicked -> {
                hasDealt = false
                viewModelScope.launch {
                    scoreManager.startNextRound(game)
                }
            }
            is UiEvent.OnFinishGameClicked -> {
                viewModelScope.launch {
                    scoreManager.finishGame(game)
                }
            }
            is UiEvent.OnEndGameClicked -> {
                viewModelScope.launch {
                    _actions.send(Action.PopBackStack)
                }
            }
        }
    }
    //endregion

    //region Helpers
    private fun updatePlayerBidsUiState() {
        val nextRoundButtonEnabled = currentRound.playerBids.sumOf { it.actual } == currentRound.number
        uiState = uiState.copy(
            forceUpdate = UUID.randomUUID().toString(),
            bids = currentRound.playerBids,
            nextRoundButtonEnabled = nextRoundButtonEnabled
        )
    }
    //endregion
}