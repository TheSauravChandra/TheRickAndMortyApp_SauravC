package com.saurav.therickandmorty_sauravc.retrofit

import com.mocklets.pluto.PlutoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val BASE_URL = "https://rickandmortyapi.com"
    private var apiInterface: ApiInterface? = null

    private fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(PlutoInterceptor())
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