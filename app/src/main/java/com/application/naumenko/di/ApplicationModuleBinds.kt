package com.application.naumenko.di

import com.application.naumenko.data.AppRepository
import com.application.naumenko.data.AppRepositoryImpl
import com.application.naumenko.domain.interactors.FilmInteractor
import com.application.naumenko.domain.interactors.FilmInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModuleBinds {

    @Binds
    abstract fun bindAppRepository(
        appRepositoryImpl: AppRepositoryImpl
    ): AppRepository

    @Binds
    abstract fun bindFilmInteractor(
        filmInteractorImpl: FilmInteractorImpl
    ): FilmInteractor
}