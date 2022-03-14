package ca.rjreid.wizardcompanion.data

import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import ca.rjreid.wizardcompanion.domain.models.Round
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Inject

//region Interface
interface ScoreManager {
    suspend fun startNewGame(playerNames: List<String>)
    fun getGame(): Game
    fun addOneToBid(bid: PlayerBid)
    fun subtractOneToBid(bid: PlayerBid)
}
//endregion

//region Implementation
class ScoreManagerImpl @Inject constructor(
    private val repository: WizardRepository
) : ScoreManager {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override suspend fun startNewGame(playerNames: List<String>) {
//        var players: List<Player> = playerNames
//            .toMutableList()
//            .filter { it.isNotBlank() }
//            .map {
//                Player(name = it)
//            }
//
//        coroutineScope.launch {
//            players = repository.insertPlayers(players)
//        }
//
//        println(players)
    }

    override fun getGame(): Game {
        val dave = Player("Dave")
        val britt = Player("Britt")
        val riley = Player("Riley")

        return Game(
            date = Date(),
            players = listOf(

            ),
            rounds = listOf(
                Round(
                    1,
                    dave,
                    listOf(
                        PlayerBid(britt, 0, 0, 20),
                        PlayerBid(riley, 1, 1, 30),
                        PlayerBid(dave, 1, 0, -10)
                    )
                ),
                Round(
                    2,
                    britt,
                    listOf(
                        PlayerBid(riley, 2, null, 30),
                        PlayerBid(dave, 1, null, -10),
                        PlayerBid(britt, 0, null, 20)
                    )
                )
            )
        )
    }

    override fun addOneToBid(bid: PlayerBid) {
        val game = getGame()
        game
            .rounds
            .last()
            .playerBids
            .first { it == bid }
            .bid
            .plus(1)
    }

    override fun subtractOneToBid(bid: PlayerBid) {
        val game = getGame()
        game
            .rounds
            .last()
            .playerBids
            .first { it == bid }
            .bid
            .minus(1)
    }
}
//endregion