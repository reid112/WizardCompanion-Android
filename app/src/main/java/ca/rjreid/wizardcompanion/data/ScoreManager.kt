package ca.rjreid.wizardcompanion.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

//region Interface
interface ScoreManager {
    fun startNewGame(playerNames: List<String>)
}
//endregion

//region Implementation
class ScoreManagerImpl @Inject constructor(
    private val repository: WizardRepository
) : ScoreManager {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun startNewGame(playerNames: List<String>) {
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
}
//endregion