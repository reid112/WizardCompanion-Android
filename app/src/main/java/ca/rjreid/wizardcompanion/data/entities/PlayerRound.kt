package ca.rjreid.wizardcompanion.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerRound(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val roundId: Int,
    val playerId: Int,
    val bet: Int,
    val actual: Int,
    val endOfRoundScore: Int,
)
