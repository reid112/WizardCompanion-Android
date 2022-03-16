package ca.rjreid.wizardcompanion.data.models.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import ca.rjreid.wizardcompanion.data.models.entities.PlayerBidDto
import ca.rjreid.wizardcompanion.data.models.entities.PlayerDto

data class PlayerBidWithPlayer(
    @Embedded
    var playerBid: PlayerBidDto,

    @Relation(
        parentColumn = "player_id",
        entityColumn = "id"
    )
    var player: PlayerDto,
)