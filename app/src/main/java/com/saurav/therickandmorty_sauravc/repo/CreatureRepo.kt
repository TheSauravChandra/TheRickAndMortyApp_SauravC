package com.saurav.therickandmorty_sauravc.repo

import com.saurav.therickandmorty_sauravc.retrofit.ApiInterface

class CreatureRepo constructor(private val retrofitService: ApiInterface) {

    suspend fun getAllCreatures(pageNo: Int) = retrofitService.getAllCreatures(pageNo)

}