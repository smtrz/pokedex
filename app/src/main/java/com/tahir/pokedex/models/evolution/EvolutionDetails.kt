package com.tahir.pokedex.models.evolution

import com.google.gson.annotations.SerializedName


data class EvolutionDetails (

  @SerializedName("gender"                  ) var gender                : String?  = null,
  @SerializedName("held_item"               ) var heldItem              : String?  = null,
  @SerializedName("item"                    ) var item                  : Item?    = Item(),
  @SerializedName("known_move"              ) var knownMove             : String?  = null,
  @SerializedName("known_move_type"         ) var knownMoveType         : String?  = null,
  @SerializedName("location"                ) var location              : String?  = null,
  @SerializedName("min_affection"           ) var minAffection          : String?  = null,
  @SerializedName("min_beauty"              ) var minBeauty             : String?  = null,
  @SerializedName("min_happiness"           ) var minHappiness          : String?  = null,
  @SerializedName("min_level"               ) var minLevel              : String?  = null,
  @SerializedName("needs_overworld_rain"    ) var needsOverworldRain    : Boolean? = null,
  @SerializedName("party_species"           ) var partySpecies          : String?  = null,
  @SerializedName("party_type"              ) var partyType             : String?  = null,
  @SerializedName("relative_physical_stats" ) var relativePhysicalStats : String?  = null,
  @SerializedName("time_of_day"             ) var timeOfDay             : String?  = null,
  @SerializedName("trade_species"           ) var tradeSpecies          : String?  = null,
  @SerializedName("trigger"                 ) var trigger               : Trigger? = Trigger(),
  @SerializedName("turn_upside_down"        ) var turnUpsideDown        : Boolean? = null

)