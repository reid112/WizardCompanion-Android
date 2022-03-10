package ca.rjreid.wizardcompanion.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ca.rjreid.wizardcompanion.data.models.Game
import ca.rjreid.wizardcompanion.data.models.Round
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM game WHERE id = :gameId")
    suspend fun getGameById(gameId: Int): Game?

    @Query(
        "SELECT * FROM game " +
                "JOIN round ON game.id = round.gameId " +
                "WHERE game.id = :gameId"
    )
    suspend fun getGameWithDetails(gameId: Int): Map<Game, List<Round>>

    @Query(
        "SELECT * FROM game " +
                "JOIN round ON game.id = round.gameId " +
                "WHERE game.winnerId != null"
    )
    fun getPastGamesWithDetails(): Flow<Map<Game, List<Round>>>
}