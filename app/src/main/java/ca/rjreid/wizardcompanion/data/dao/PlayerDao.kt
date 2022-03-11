package ca.rjreid.wizardcompanion.data.dao

import androidx.room.Dao
import androidx.room.Insert
import ca.rjreid.wizardcompanion.data.entities.Player

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayers(players: List<Player>)
}