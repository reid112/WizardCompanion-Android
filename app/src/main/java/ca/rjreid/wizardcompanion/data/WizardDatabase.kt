package ca.rjreid.wizardcompanion.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ca.rjreid.wizardcompanion.data.dao.GameDao
import ca.rjreid.wizardcompanion.data.dao.PlayerDao
import ca.rjreid.wizardcompanion.data.dao.RoundDao
import ca.rjreid.wizardcompanion.data.models.Game
import ca.rjreid.wizardcompanion.data.models.Player
import ca.rjreid.wizardcompanion.data.models.PlayerRound
import ca.rjreid.wizardcompanion.data.models.Round
import ca.rjreid.wizardcompanion.data.utils.Converters

@Database(
    entities = [
        Game::class,
        Player::class,
        PlayerRound::class,
        Round::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WizardDatabase : RoomDatabase() {
    abstract val gameDao: GameDao
    abstract val roundDao: RoundDao
    abstract val playerDao: PlayerDao
}