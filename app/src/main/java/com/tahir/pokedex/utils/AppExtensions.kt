package com.tahir.pokedex.utils

import android.content.Context
import android.widget.Toast
import com.tahir.pokedex.constants.AppConstants

// toast helper Extensions
fun Context.toast(message: String) {
	Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// extract id from the url
fun String.extractId() = this.substringAfter("pokemon-species").replace("/", "").toInt()

//extract id from Evolution chain url
fun String.extractEvolutionId() = this.substringAfter("evolution-chain").replace("/", "").toInt()

// get the picture url
fun String.getPicUrl(): String {
	val id = this.extractId()
	return AppConstants.POKEMON_IMG_PREFIX + "/${id}.png"
}


