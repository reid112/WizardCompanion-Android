package ca.rjreid.wizardcompanion.data

import ca.rjreid.wizardcompanion.data.dao.GameDao
import ca.rjreid.wizardcompanion.data.models.entities.*
import ca.rjreid.wizardcompanion.data.models.entities.relations.GameWithPlayersAndRounds
import kotlinx.coroutines.flow.Flow

interface WizardRepository {
    fun getGameWithDetails(gameId: Long): Flow<GameWithPlayersAndRounds?>
    suspend fun startNameGame(players: List<PlayerDto>): Long
    suspend fun startNewRound(rounds: List<RoundDto>, currentBids: List<PlayerBidDto>, newBids: List<PlayerBidDto>)
    suspend fun updateGame(game: GameDto)
    suspend fun updateRounds(rounds: List<RoundDto>)
    suspend fun updatePlayerBids(playerBids: List<PlayerBidDto>)
//    fun getPastGamesWithDetails(): Flow<Map<GameDto, List<RoundDto>>>
}

class WizardRepositoryImpl(
    private val gameDao: GameDao,
) : WizardRepository {
    override fun getGameWithDetails(gameId: Long): Flow<GameWithPlayersAndRounds?> {
        return gameDao.getGameById(gameId)
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
                gameDao.updatePlayerBid(it)
            }
        }
    }

    //    override fun getPastGamesWithDetails(): Flow<Map<GameDto, List<RoundDto>>> {
//        return gameDao.getPastGamesWithDetails()
//    }
}