package com.leandroid.system.rentacarmanagement.data.api

import com.leandroid.system.rentacarmanagement.BuildConfig
import com.leandroid.system.rentacarmanagement.data.api.adapter.NetworkResponseAdapterFactory
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory

class Retrofit {
    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            baseUrl: String
        ): retrofit2.Retrofit {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .header("Accept", "application/json")
                    .header( "Content-Type", "application/json")
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

//            val apiURL = "http://10.0.2.2:3003/omapi/"
//            val apiURL = "https://mcs-dash-be-prod.herokuapp.com/omapi/"

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .build()

            val moshi =
                 Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return retrofit2.Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }
    }
}