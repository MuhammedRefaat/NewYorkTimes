package com.mango.nytimes.network

import com.mango.nytimes.BuildConfig
import com.mango.nytimes.misc.AppConstants
import dagger.Module
import dagger.Provides

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Muhammed Refaat on 30/1/2021.
 */
@Module
object APIsClient {

    private const val CONNECT_TIMEOUT = 10 // in secs
    private const val READ_WRITE_TIMEOUT = 100 // in secs

    @Provides
    fun initClient(): APIsInterface {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.readTimeout(READ_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.writeTimeout(READ_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClient.addInterceptor(loggingInterceptor)
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .baseUrl(AppConstants.APIS_URL).build()
            .create(APIsInterface::class.java)
    }

    @Provides
    fun provideAPIsCallService(): APIsCaller {
        return APIsCaller()
    }

}
