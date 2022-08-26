package com.tahir.pokedex.repository

import com.tahir.pokedex.generics.BaseApiResponse
import com.tahir.pokedex.generics.ResponseResult
import com.tahir.pokedex.models.details.PokemonDetails
import com.tahir.pokedex.models.evolution.PokemonEvolution
import com.tahir.pokedex.models.species.Species
import com.tahir.pokedex.utils.applyCommonSideEffects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository is the Single Source of truth that contains instance of remote data
 * source inherits from BaseApiResponse.
 * @constructor remoteDataSource (using construction injection)
 */
@Singleton
class Repository
@Inject
constructor(
		private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

	/**
	 * calls API for paginated pokemons
	 * @param offset, limit
	 * @return Species
	 */
	suspend fun getPaginatedPokemons(offset: Int, limit: Int): Species {
		return remoteDataSource.getPaginatedPPokemonSpecies(offset, limit)

	}


	/**
	 * calls API for pokemon details, emits data on IO Dispatcher.
	 * @param id
	 * @return Flow<ResponseResult<PokemonDetails>>
	 */
	fun getPokemonDetails(id: Int): Flow<ResponseResult<PokemonDetails>> {
		return flow {
			emit(safeApiCall { remoteDataSource.getPokemonDetails(id) })

		}.flowOn(Dispatchers.IO)
				.applyCommonSideEffects().catch {
					emit(ResponseResult.Error("Error occured"))
				}

	}

	/**
	 * calls API for pokemon evolution chain, emits data on IO Dispatcher.
	 * @param id
	 * @return Flow<ResponseResult<PokemonEvolution>>
	 */
	fun getPokemonEvolutionChain(id: Int): Flow<ResponseResult<PokemonEvolution>> {
		return flow {
			emit(safeApiCall { remoteDataSource.getPokemonEvolution(id) })

		}.flowOn(Dispatchers.IO)
				.applyCommonSideEffects().catch {
					emit(ResponseResult.Error("Error occured"))
				}

	}

}
