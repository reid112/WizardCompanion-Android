package ca.rjreid.wizardcompanion.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Round(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val gameId: Int,
    val roundNumber: Int,
)