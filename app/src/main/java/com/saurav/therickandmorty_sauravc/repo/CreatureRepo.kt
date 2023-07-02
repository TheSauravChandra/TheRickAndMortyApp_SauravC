package com.saurav.therickandmorty_sauravc.repo

import com.saurav.therickandmorty_sauravc.retrofit.ApiInterface

class CreatureRepo constructor(private val apiInterface: ApiInterface) {

    suspend fun getAllCreatures(pageNo: Int?) = apiInterface.getAllCreatures(pageNo)

}