package ca.rjreid.wizardcompanion.data

import ca.rjreid.wizardcompanion.domain.mappers.*
import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import ca.rjreid.wizardcompanion.domain.models.Round
import ca.rjreid.wizardcompanion.util.TOTAL_CARDS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.abs

//region Interface
interface ScoreManager {
    fun getGameDetails(): Flow<Game>
    fun getTotalRounds(game: Game): Int
    suspend fun startNewGame(playerNames: List<String>)
    suspend fun startNextRound(game: Game)
    suspend fun finishGame(game: Game)
    suspend fun updateGame(game: Game)
}
//endregion

//region Implementation
class ScoreManagerImpl @Inject constructor(
    private val repository: WizardRepository
) : ScoreManager {
    private var gameId: Long? = null

    override fun getGameDetails(): Flow<Game> {
        return repository
            .getGameWithDetails(gameId ?: 0)
            .filterNotNull()
            .map { it.toGame() }
    }

    override fun getTotalRounds(game: Game): Int = TOTAL_CARDS / game.players.size

    override suspend fun startNewGame(playerNames: List<String>) {
        val players: List<Player> = playerNames
            .toMutableList()
            .filter { it.isNotBlank() }
            .map {
                Player(id = 0, name = it)
            }

        gameId = repository.startNameGame(players.map { it.toPlayerDto() })
    }

    override suspend fun startNextRound(game: Game) {
        val lastRound = game.rounds.last()
        calculateRoundScore(lastRound)

        val playerBids = lastRound.playerBids.map {
            PlayerBid(
                id = 0,
                player = it.player,
                score = it.score
            )
        }
        val newRound = Round(
            id = 0,
            number = lastRound.number + 1,
            dealer = getNextDealer(game),
            playerBids = playerBids
        )

        val mutableRounds = game.rounds.toMutableList()
        mutableRounds.add(newRound)
        game.rounds = mutableRounds

        repository.startNewRound(
            rounds = game.rounds.map { it.toRoundDto(game.id) },
            currentBids = lastRound.playerBids.map { it.toPlayerBidDto(lastRound.id) },
            newBids = game.rounds.last().playerBids.map { it.toPlayerBidDto(0) }
        )
    }

    override suspend fun finishGame(game: Game) {
        val lastRound = game.rounds.last()
        calculateRoundScore(lastRound)
        game.winner = game.rounds.last().playerBids.sortedBy { it.score }.last().player
        repository.updateGame(game.toGameDto())
        repository.updateRounds(game.rounds.map { it.toRoundDto(game.id) })
        repository.updatePlayerBids(game.rounds.last().playerBids.map { it.toPlayerBidDto(lastRound.id) })
    }

    override suspend fun updateGame(game: Game) {
        gameId?.let { id ->
            repository.updateGame(game.toGameDto())
            repository.updateRounds(game.rounds.map { it.toRoundDto(id) })

            game.rounds.forEach { round ->
                repository.updatePlayerBids(round.playerBids.map { it.toPlayerBidDto(round.id) })
            }
        }
    }

    private fun getNextDealer(game: Game): Player {
        val currentDealer = game.rounds.last().dealer
        val currentDealerIndex = game.players.indexOf(currentDealer)
        val nextDealerIndex = if (currentDealerIndex == game.players.lastIndex) {
            0
        } else {
            currentDealerIndex + 1
        }

        return game.players[nextDealerIndex]
    }

    private fun calculateRoundScore(round: Round) {
        round.playerBids.forEach {
            it.score = if (it.bid == it.actual) {
                it.score + (20 + (it.actual * 10))
            } else {
                it.score + (abs(it.bid - it.actual) * -10)
            }
        }
    }
}
//endregion