package ca.rjreid.wizardcompanion.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ca.rjreid.wizardcompanion.data.dao.GameDao
import ca.rjreid.wizardcompanion.data.models.entities.*
import ca.rjreid.wizardcompanion.data.utils.Converters

@Database(
    entities = [
        GameDto::class,
        GamePlayersDto::class,
        PlayerDto::class,
        PlayerBidDto::class,
        RoundDto::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WizardDatabase : RoomDatabase() {
    abstract val gameDao: GameDao
}