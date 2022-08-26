package com.tahir.pokedex.repository

import com.tahir.pokedex.api.PokemonService
import javax.inject.Inject

/**
 * all operations related to fetching data from online are contained in RemoteDataSource
 * @constructor pokemonService
 */
class RemoteDataSource @Inject constructor(private val pokemonService: PokemonService) {
	suspend fun getPaginatedPPokemonSpecies(offset: Int, limit: Int) =
			pokemonService.gePaginatedtSpecies(offset, limit)
	// suspended function that gets the pokemon species

	// suspended function that gets the pokemon details
	suspend fun getPokemonDetails(id: Int) =
			pokemonService.getSpeciesDetails(id)

	// suspended function that gets the pokemon evolution chain
	suspend fun getPokemonEvolution(id: Int) =
			pokemonService.getEvolutionChain(id)
}