package com.tahir.pokedex.constants


class AppConstants {
	companion object {
		const val BASE_URL = "https://pokeapi.co/api/v2/"
		const val POKEMON_IMG_PREFIX = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
		const val MAX_RETRIES = 3L
		const val INITIAL_BACKOFF = 2000L
		const val STARTING_OFFSET_INDEX = 0
		const val PAGE_SIZE = 100

		// API Constants path variables
		const val EVOLUTION_CHAIN = "evolution-chain/{id}"
		const val SPECIES = "pokemon-species"
		const val SPECIES_DETAILS = "pokemon-species/{id}"


	}
}