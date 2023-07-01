package com.saurav.therickandmorty_sauravc.retrofit

import com.saurav.therickandmorty_sauravc.beans.AllCreaturesResponse
import com.saurav.therickandmorty_sauravc.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(Constants.RICK_N_MORTY)
    suspend fun getAllCreatures(@Query("page") page: Int?): Response<AllCreaturesResponse>

}