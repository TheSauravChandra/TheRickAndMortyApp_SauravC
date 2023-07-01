package com.saurav.therickandmorty_sauravc.beans


import com.google.gson.annotations.SerializedName

data class AllCreaturesResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val creatures: List<Creature>
)