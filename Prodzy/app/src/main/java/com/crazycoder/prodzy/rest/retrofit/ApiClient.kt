package com.crazycoder.prodzy.rest.retrofit

import com.crazycoder.prodzy.BuildConfig
import com.crazycoder.prodzy.rest.retrofit.network.NetworkInterceptor
import com.crazycoder.prodzy.utils.MainApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private var retrofit: Retrofit? = null
    private val REQUEST_TIMEOUT = 20

    private fun getHttpClient(): OkHttpClient {
        val appContext = MainApplication.applicationContext()

        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        // Interceptor to handle logging
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        httpClient.addInterceptor(logging)

        // Interceptor to handle Network Connection
        httpClient.addInterceptor(NetworkInterceptor(appContext))

        return httpClient.build()
    }

    fun getClient(): Retrofit? {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ApiUrlFactory.BaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .client(getHttpClient())
                .build()
        }
        return retrofit
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit!!.create(serviceClass)
    }

}