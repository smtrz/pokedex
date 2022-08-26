package com.tahir.pokedex.models.evolution

import com.google.gson.annotations.SerializedName


data class Chain (

  @SerializedName("evolution_details" ) var evolutionDetails : ArrayList<String>    = arrayListOf(),
  @SerializedName("evolves_to"        ) var evolvesTo        : ArrayList<EvolvesTo> = arrayListOf(),
  @SerializedName("is_baby"           ) var isBaby           : Boolean?             = null,
  @SerializedName("species"           ) var species          : Species?             = Species()

)