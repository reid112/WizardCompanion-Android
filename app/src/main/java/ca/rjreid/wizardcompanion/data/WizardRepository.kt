package ca.rjreid.wizardcompanion.data

import ca.rjreid.wizardcompanion.data.dao.GameDao
import ca.rjreid.wizardcompanion.data.dao.PlayerDao
import ca.rjreid.wizardcompanion.data.dao.RoundDao
import ca.rjreid.wizardcompanion.data.models.Game
import ca.rjreid.wizardcompanion.data.models.Player
import ca.rjreid.wizardcompanion.data.models.Round
import kotlinx.coroutines.flow.Flow

interface WizardRepository {
    //region Player
    suspend fun insertPlayer(player: Player)
    //endregion

    //region Round
    suspend fun insertRound(round: Round)
    suspend fun getRoundById(roundId: Int): Round?
    //endregion

    //region Game
    suspend fun insertGame(game: Game)
    suspend fun getGameById(gameId: Int): Game?
    suspend fun getGameWithDetails(gameId: Int): Map<Game, List<Round>>
    fun getPastGamesWithDetails(): Flow<Map<Game, List<Round>>>
    //endregion
}

class WizardRepositoryImpl(
    private val playerDao: PlayerDao,
    private val roundDao: RoundDao,
    private val gameDao: GameDao
) : WizardRepository {
    //region Player
    override suspend fun insertPlayer(player: Player) {
        playerDao.insertPlayer(player)
    }
    //endregion

    //region Round
    override suspend fun insertRound(round: Round) {
        roundDao.insertRound(round)
    }

    override suspend fun getRoundById(roundId: Int): Round? {
        return roundDao.getRoundById(roundId)
    }
    //endregion

    //region Game
    override suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    override suspend fun getGameById(gameId: Int): Game? {
        return gameDao.getGameById(gameId)
    }

    override suspend fun getGameWithDetails(gameId: Int): Map<Game, List<Round>> {
        return gameDao.getGameWithDetails(gameId)
    }

    override fun getPastGamesWithDetails(): Flow<Map<Game, List<Round>>> {
        return gameDao.getPastGamesWithDetails()
    }
    //endregion
}