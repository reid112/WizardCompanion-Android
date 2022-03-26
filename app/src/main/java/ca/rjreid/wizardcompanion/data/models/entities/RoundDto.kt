package ca.rjreid.wizardcompanion.data.models.entities

import androidx.room.*

@Entity(
    tableName = "rounds",
    foreignKeys = [
        ForeignKey(
            entity = GameDto::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("game_id")]
)
data class RoundDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "game_id")
    var gameId: Long,

    @ColumnInfo(name = "round_number")
    var roundNumber: Int = 1,

    @ColumnInfo(name = "dealer_id")
    var dealerId: Long
)