package ca.rjreid.wizardcompanion.data.models.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ca.rjreid.wizardcompanion.data.models.entities.GameDto
import ca.rjreid.wizardcompanion.data.models.entities.GamePlayersDto
import ca.rjreid.wizardcompanion.data.models.entities.PlayerDto
import ca.rjreid.wizardcompanion.data.models.entities.RoundDto

data class GameWithPlayersAndRounds(
    @Embedded
    var game: GameDto,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = GamePlayersDto::class,
            parentColumn = "game_id",
            entityColumn = "player_id")
    )
    var players: List<PlayerDto>,

    @Relation(
        parentColumn = "id",
        entityColumn = "game_id",
        entity = RoundDto::class
    )
    var rounds: List<RoundWithDealer>,

    @Relation(
        parentColumn = "winner_id",
        entityColumn = "id"
    )
    var winner: PlayerDto? = null
)