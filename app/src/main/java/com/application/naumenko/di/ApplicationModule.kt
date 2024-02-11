package com.application.naumenko.di

import android.content.Context
import androidx.room.Room
import com.application.naumenko.data.AppRepositoryImpl
import com.application.naumenko.data.network.FilmApiResponse
import com.application.naumenko.data.database.AppDatabase
import com.application.naumenko.data.database.FilmDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): FilmApiResponse = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(FilmApiResponse::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "appDatabase"
        ).build()

    @Provides
    @Singleton
    fun provideFilmDetailsDao(appDatabase: AppDatabase): FilmDetailsDao =
        appDatabase.filmDetailDao()

    @Provides
    @Singleton
    fun provideAppRepository(
        filmApiResponse: FilmApiResponse, filmDetailsDao: FilmDetailsDao
    ): AppRepositoryImpl =
        AppRepositoryImpl(filmApiResponse = filmApiResponse, filmDetailsDao = filmDetailsDao)
}