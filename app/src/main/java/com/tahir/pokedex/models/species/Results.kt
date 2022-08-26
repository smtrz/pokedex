package com.tahir.pokedex.models.species

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Results(

    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null

) : Parcelable
