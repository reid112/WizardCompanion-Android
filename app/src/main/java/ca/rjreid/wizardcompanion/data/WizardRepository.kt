package ca.rjreid.wizardcompanion.data

import ca.rjreid.wizardcompanion.data.dao.GameDao
import ca.rjreid.wizardcompanion.data.models.ThemeSetting
import ca.rjreid.wizardcompanion.data.models.entities.*
import ca.rjreid.wizardcompanion.data.models.entities.relations.GameWithPlayersAndRounds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

interface WizardRepository {
    fun getThemeSetting(): Flow<ThemeSetting>
    suspend fun setThemeSetting(theme: ThemeSetting)
    fun getKeepScreenOnSetting(): Flow<Boolean>
    suspend fun setKeepScreenOnSetting(keepScreenOn: Boolean)

    fun getGameWithDetails(gameId: Long): Flow<GameWithPlayersAndRounds?>
    fun getPastGamesWithDetails(): Flow<List<GameWithPlayersAndRounds>>
    fun getInProgressGame(): Flow<GameWithPlayersAndRounds?>

    suspend fun startNameGame(players: List<PlayerDto>): Long
    suspend fun startNewRound(rounds: List<RoundDto>, currentBids: List<PlayerBidDto>, newBids: List<PlayerBidDto>)
    suspend fun updateGame(game: GameDto)
    suspend fun updateRounds(rounds: List<RoundDto>)
    suspend fun updatePlayerBids(playerBids: List<PlayerBidDto>)
    suspend fun removeAllInProgressGames()
}

class WizardRepositoryImpl(
    private val wizardSettings: WizardSettings,
    private val gameDao: GameDao,
) : WizardRepository {
    override fun getThemeSetting(): Flow<ThemeSetting> {
        return wizardSettings
            .getThemePref()
            .filterNotNull()
            .map { ThemeSetting.fromInt(it) ?: ThemeSetting.SYSTEM }
    }

    override suspend fun setThemeSetting(theme: ThemeSetting) {
        wizardSettings.setThemePref(theme)
    }

    override fun getKeepScreenOnSetting(): Flow<Boolean> {
        return wizardSettings
            .getKeepScreenOnPref()
            .filterNotNull()
    }

    override suspend fun setKeepScreenOnSetting(keepScreenOn: Boolean) {
        wizardSettings.setKeepScreenOnPref(keepScreenOn)
    }

    override fun getGameWithDetails(gameId: Long): Flow<GameWithPlayersAndRounds?> {
        return gameDao.getGameById(gameId)
    }

    override fun getPastGamesWithDetails(): Flow<List<GameWithPlayersAndRounds>> {
        return gameDao.getPastGames()
    }

    override fun getInProgressGame(): Flow<GameWithPlayersAndRounds?> {
        return gameDao.getInProgressGame()
    }

    override suspend fun startNameGame(players: List<PlayerDto>): Long {
        // Insert a new game
        val game = GameDto()
        game.id = gameDao.insertGame(game)

        // Insert all players and update their id
        players.forEach { player ->
            player.id = gameDao.insertPlayer(player)
            gameDao.insertGamePlayer(GamePlayersDto(
                gameId = game.id,
                playerId = player.id
            ))
        }

        // Insert the first round
        val round = RoundDto(gameId = game.id, dealerId = players.first().id)
        round.id = gameDao.insertRound(round)

        // Insert player bids for first round
        players.forEach { player ->
            gameDao.insertPlayerBid(PlayerBidDto(
                playerId = player.id,
                roundId = round.id,
            ))
        }

        return game.id
    }

    override suspend fun startNewRound(
        rounds: List<RoundDto>,
        currentBids: List<PlayerBidDto>,
        newBids: List<PlayerBidDto>
    ) {
        var newRoundId: Long = 0
        rounds.forEach { round ->
            if (round.id > 0) {
                gameDao.updateRound(round)
            } else {
                newRoundId = gameDao.insertRound(round)
            }
        }

        currentBids.forEach { playerBid ->
            gameDao.updatePlayerBid(playerBid)
        }

        newBids.forEach { playerBid ->
            playerBid.roundId = newRoundId
            gameDao.insertPlayerBid(playerBid)
        }
    }

    override suspend fun updateGame(game: GameDto) {
        gameDao.updateGame(game)
    }

    override suspend fun updateRounds(rounds: List<RoundDto>) {
        rounds.forEach {
            if (it.id > 0) {
                gameDao.updateRound(it)
            } else {
                gameDao.insertRound(it)
            }
        }
    }

    override suspend fun updatePlayerBids(playerBids: List<PlayerBidDto>) {
        playerBids.forEach {
            if (it.id > 0) {
                gameDao.updatePlayerBid(it)
            } else {
                gameDao.insertPlayerBid(it)
            }
        }
    }

    override suspend fun removeAllInProgressGames() {
        gameDao.removeAllInProgressGames()
    }
}