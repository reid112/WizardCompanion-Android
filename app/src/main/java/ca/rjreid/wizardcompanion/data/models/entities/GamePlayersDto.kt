package ca.rjreid.wizardcompanion.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "game_players",
    primaryKeys = ["game_id", "player_id"],
    foreignKeys = [
        ForeignKey(
            entity = GameDto::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerDto::class,
            parentColumns = ["id"],
            childColumns = ["player_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("game_id"),
        Index("player_id")
    ]
)
data class GamePlayersDto(
    @ColumnInfo(name = "game_id")
    var gameId: Long,

    @ColumnInfo(name = "player_id")
    var playerId: Long,
)