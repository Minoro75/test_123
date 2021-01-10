package io.minoro75.test_123.ui.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.minoro75.test_123.ui.remote.ApiRemoteDataSource
import io.minoro75.test_123.ui.remote.ApiService
import io.minoro75.test_123.ui.remote.ApiServiceHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideGson(): Gson =
        GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.privatbank.ua/")
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiServiceHelper(apiRemoteDataSource: ApiRemoteDataSource): ApiServiceHelper =
        apiRemoteDataSource

}