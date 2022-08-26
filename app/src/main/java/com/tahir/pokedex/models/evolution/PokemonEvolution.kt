package com.tahir.pokedex.models.evolution

import com.google.gson.annotations.SerializedName


data class PokemonEvolution (

    @SerializedName("baby_trigger_item" ) var babyTriggerItem : String? = null,
    @SerializedName("chain"             ) var chain           : Chain?  = Chain(),
    @SerializedName("id"                ) var id              : Int?    = null

)