package com.saurav.therickandmorty_sauravc.retrofit

import com.mocklets.pluto.PlutoInterceptor
import com.saurav.therickandmorty_sauravc.application.AppContext
import com.saurav.therickandmorty_sauravc.interceptors.CacheInterceptor
import com.saurav.therickandmorty_sauravc.interceptors.ForceCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitHelper {
    private val BASE_URL = "https://rickandmortyapi.com"
    private var apiInterface: ApiInterface? = null

    private fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(PlutoInterceptor())
            .cache(Cache(File(AppContext.getContext().cacheDir, "http-cache"), 10L * 1024L * 1024L)) // 10 MiB
            .addNetworkInterceptor(CacheInterceptor()) // only if Cache-Control header is not enabled from the server
            .addInterceptor(ForceCacheInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): ApiInterface {
        if (apiInterface == null)
            apiInterface = getRetrofit().create(ApiInterface::class.java)

        return apiInterface!!
    }

}