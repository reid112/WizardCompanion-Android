package ca.rjreid.wizardcompanion.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ca.rjreid.wizardcompanion.data.entities.Round

@Dao
interface RoundDao {
    @Insert
    suspend fun insertRound(round: Round)

    @Query("SELECT * FROM round WHERE id = :roundId")
    suspend fun getRoundById(roundId: Int): Round?
}