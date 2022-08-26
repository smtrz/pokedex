package com.tahir.pokedex.api


import com.tahir.pokedex.constants.AppConstants
import com.tahir.pokedex.models.details.PokemonDetails
import com.tahir.pokedex.models.evolution.PokemonEvolution
import com.tahir.pokedex.models.species.Species
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * pokemon Service contains suspend methods for the network calls
 */
interface PokemonService {
	/**
	 * getPaginatedSpecies
	 * @param offset , limit
	 * @return Species
	 */
	@GET(AppConstants.SPECIES)
	suspend fun gePaginatedtSpecies(
			@Query("offset") offset: Int, @Query("limit") limit: Int = 100
	): Species

	/**
	 * getSpeciesDetails
	 * @param id
	 * @return Response<PokemonDetails>
	 */
	@GET(AppConstants.SPECIES_DETAILS)
	suspend fun getSpeciesDetails(
			@Path("id") id: Int
	): Response<PokemonDetails>

	/**
	 * getEvolutionChain
	 * @param id
	 * @return Response<PokemonEvolution>
	 */
	@GET(AppConstants.EVOLUTION_CHAIN)
	suspend fun getEvolutionChain(
			@Path("id") id: Int
	): Response<PokemonEvolution>
}