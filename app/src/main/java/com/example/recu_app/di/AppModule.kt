package com.example.recu_app.di

import android.content.Context
import androidx.room.Room
import com.example.recu_app.data.alerts.database.dao.AlertsDao
import com.example.recu_app.data.alerts.database.AlertsDatabase
import com.example.recu_app.utils.Migrations.MIGRATION_2_3
import com.example.recu_app.domain.alerts.models.AlertsRepository
import com.example.recu_app.domain.alerts.models.AlertsRepositoryImpl
import com.example.recu_app.domain.alerts.usecase.DeleteAlertUseCase
import com.example.recu_app.domain.alerts.usecase.GetAllAlertsUseCase
import com.example.recu_app.domain.alerts.usecase.SaveAlertUseCase
import com.example.recu_app.utils.dispatchers.DefaultDispatchers
import com.example.recu_app.utils.dispatchers.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAlertDatabase(
        @ApplicationContext context: Context,
    ): AlertsDatabase = Room.databaseBuilder(
        context,
        AlertsDatabase::class.java,
        "alerts.db"
    ).addMigrations(MIGRATION_2_3)
        .build()

    @Singleton
    @Provides
    fun provideAlertDao(database: AlertsDatabase): AlertsDao = database.dao()

    @Singleton
    @Provides
    fun provideAlertRepository(dao: AlertsDao): AlertsRepository {
        return AlertsRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DefaultDispatchers()
    }

    @Provides
    fun provideSaveAlertUseCase(repository: AlertsRepository): SaveAlertUseCase {
        return SaveAlertUseCase(repository)
    }

    @Provides
    fun provideDeleteAlertUseCase(repository: AlertsRepository): DeleteAlertUseCase {
        return DeleteAlertUseCase(repository)
    }

    @Provides
    fun provideGetAllAlertsUseCase(repository: AlertsRepository): GetAllAlertsUseCase {
        return GetAllAlertsUseCase(repository)
    }
}
