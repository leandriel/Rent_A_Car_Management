package com.leandroid.system.rentacarmanagement.data.api

import com.leandroid.system.rentacarmanagement.BuildConfig
import com.leandroid.system.rentacarmanagement.data.api.utils.ConnectivityInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

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
                    //.addQueryParameter("key", API_KEY)
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

//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .addInterceptor(requestInterceptor)
//                .addInterceptor(connectivityInterceptor)
//                .build()
//            val apiURL = baseUrl.getProductionBaseUrl()
            //val apiURL = baseUrl.getLocalHostBaseUrl()

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

//            val gson = GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .setLenient()
//                .create()

            return retrofit2.Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                //.baseUrl("https://gaos-backend.herokuapp.com/api/mobile/")
                //.addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addCallAdapterFactory(FlowCallAdapterFactory.create())
//                .addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}