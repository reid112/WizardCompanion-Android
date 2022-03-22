package ca.rjreid.wizardcompanion.data.dao

import androidx.room.*
import ca.rjreid.wizardcompanion.data.models.entities.*
import ca.rjreid.wizardcompanion.data.models.entities.relations.GameWithPlayersAndRounds
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Transaction
    @Query("SELECT * FROM games WHERE id = :gameId")
    fun getGameById(gameId: Long): Flow<GameWithPlayersAndRounds>

    @Transaction
    @Query("SELECT * FROM games WHERE winner_id != null")
    fun getPastGames(): Flow<List<GameWithPlayersAndRounds>>

    @Insert
    suspend fun insertGame(game: GameDto): Long

    @Insert
    suspend fun insertRound(round: RoundDto): Long

    @Insert
    suspend fun insertPlayer(player: PlayerDto): Long

    @Insert
    suspend fun insertPlayerBid(playerBid: PlayerBidDto): Long

    @Insert
    suspend fun insertGamePlayer(gamePlayer: GamePlayersDto): Long

    @Update
    suspend fun updateGame(game: GameDto)

    @Update
    suspend fun updateRound(rounds: RoundDto)

    @Update
    suspend fun updatePlayer(player: PlayerDto)

    @Update
    suspend fun updatePlayerBid(playerBid: PlayerBidDto)
}