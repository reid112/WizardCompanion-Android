package ca.rjreid.wizardcompanion.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "game_players",
    primaryKeys = ["game_id", "player_id"]
)
data class GamePlayersDto(
    @ColumnInfo(name = "game_id")
    var gameId: Long,

    @ColumnInfo(name = "player_id")
    var playerId: Long,
)