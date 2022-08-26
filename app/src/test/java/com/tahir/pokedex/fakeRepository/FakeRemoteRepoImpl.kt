package com.tahir.pokedex.fakeRepository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tahir.pokedex.api.PokemonService
import com.tahir.pokedex.models.details.PokemonDetails
import com.tahir.pokedex.models.evolution.PokemonEvolution
import com.tahir.pokedex.models.species.Species
import retrofit2.Response

/** fake Implementation for PokemonService */
class FakeRemoteRepoImpl() : PokemonService {

    override suspend fun gePaginatedtSpecies(offset: Int, limit: Int): Species {

        val species = Gson().fromJson<Species>(SamplePokeMonData.getFakeSpecies())

        return species
    }

    override suspend fun getSpeciesDetails(id: Int): Response<PokemonDetails> {
        val pokemonDetails =
            Gson().fromJson<PokemonDetails>(SamplePokeMonData.getFakeSpecieDetails())

        return Response.success(pokemonDetails)
    }

    override suspend fun getEvolutionChain(id: Int): Response<PokemonEvolution> {
        val evolutionData =
            Gson().fromJson<PokemonEvolution>(SamplePokeMonData.getFakeEvolutionData())

        return Response.success(evolutionData)
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}
