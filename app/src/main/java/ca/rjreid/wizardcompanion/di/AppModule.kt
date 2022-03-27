package ca.rjreid.wizardcompanion.di

import android.app.Application
import androidx.room.Room
import ca.rjreid.wizardcompanion.WizardSettingsImpl
import ca.rjreid.wizardcompanion.data.WizardDatabase
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.data.WizardRepositoryImpl
import ca.rjreid.wizardcompanion.data.WizardSettings
import ca.rjreid.wizardcompanion.domain.ScoreManager
import ca.rjreid.wizardcompanion.domain.ScoreManagerImpl
import ca.rjreid.wizardcompanion.util.WIZARD_DATABASE_NAME
import ca.rjreid.wizardcompanion.util.WIZARD_SETINGS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWizardDatabase(app: Application): WizardDatabase {
        return Room.databaseBuilder(
            app,
            WizardDatabase::class.java,
            WIZARD_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWizardRepository(wizardSettings: WizardSettings, db: WizardDatabase): WizardRepository {
        return WizardRepositoryImpl(wizardSettings, db.gameDao)
    }

    @Provides
    @Singleton
    fun provideScoreManager(repository: WizardRepository): ScoreManager {
        return ScoreManagerImpl(repository = repository)
    }

    @Provides
    @Singleton
    fun dataStorePreferences(app: Application): WizardSettings =
        WizardSettingsImpl(context = app, name = WIZARD_SETINGS_NAME)
}