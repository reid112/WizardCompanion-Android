package ca.rjreid.wizardcompanion.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "player_bids",
//    foreignKeys = [
//        ForeignKey(entity = RoundDto::class, parentColumns = ["id"], childColumns = ["round_id"]),
//        ForeignKey(entity = PlayerDto::class, parentColumns = ["id"], childColumns = ["player_id"])
//    ],
//    indices = [
//        Index("round_id"),
//        Index("player_id")
//    ]
)
data class PlayerBidDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "round_id")
    var roundId: Long,

    @ColumnInfo(name = "player_id")
    var playerId: Long,

    @ColumnInfo(name = "bid")
    var bid: Int = 0,

    @ColumnInfo(name = "actual")
    var actual: Int = 0,

    @ColumnInfo(name = "score")
    var score: Int = 0,
)
