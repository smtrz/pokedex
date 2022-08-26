package com.tahir.pokedex.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonDetail(

    var name: String? = null,
    var flavourText: String? = null,
    var id: Int? = null,
    var evolvePokemonName: String? = null,
    var evolveePokemonImageUrl: String? = null,
    var captureDifference: Int? = null

):Parcelable
