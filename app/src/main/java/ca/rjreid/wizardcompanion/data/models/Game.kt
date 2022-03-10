package ca.rjreid.wizardcompanion.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Date = Date(),
    val winnerId: Int? = null,
)
