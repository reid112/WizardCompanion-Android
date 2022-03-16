package ca.rjreid.wizardcompanion.di

import android.app.Application
import androidx.room.Room
import ca.rjreid.wizardcompanion.data.*
import ca.rjreid.wizardcompanion.util.WIZARD_DATABASE_NAME
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
    fun provideWizardRepository(db: WizardDatabase): WizardRepository {
        return WizardRepositoryImpl(db.gameDao)
    }

    @Provides
    @Singleton
    fun provideScoreManager(repository: WizardRepository): ScoreManager {
        return ScoreManagerImpl(repository = repository)
    }
}