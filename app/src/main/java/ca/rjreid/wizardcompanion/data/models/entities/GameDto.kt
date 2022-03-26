package ca.rjreid.wizardcompanion.data.models.entities

import androidx.room.*
import java.util.*

@Entity(
    tableName = "games",
    foreignKeys = [
        ForeignKey(
            entity = PlayerDto::class,
            parentColumns = ["id"],
            childColumns = ["winner_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("winner_id")]
)
data class GameDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "date")
    var date: Date = Date(),

    @ColumnInfo(name = "winner_id")
    var winnerId: Long? = null,
)
