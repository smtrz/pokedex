package com.tahir.pokedex.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahir.pokedex.generics.ResponseResult
import com.tahir.pokedex.models.PokemonDetail
import com.tahir.pokedex.repository.Repository
import com.tahir.pokedex.utils.cancelIfActive
import com.tahir.pokedex.utils.extractEvolutionId
import com.tahir.pokedex.utils.extractId
import com.tahir.pokedex.utils.getPicUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val PokemonRepository: Repository) :
		ViewModel() {
	private var getPokemonJob: Job? = null
	var pokemonInfo = mutableStateOf<PokemonDetail>(PokemonDetail("", "", 0, "", "", 0))
	var isLoading = mutableStateOf(false)
	var loadError = mutableStateOf("")
	var evolutionId: Int = 0
	var pokemonName = ""
	var flavourText = ""
	var initialCaptureRate = 0
	var evolveCaptureRate = 0
	var evolves = true
	val loadState: MutableLiveData<Boolean> = MutableLiveData(false)


	/**
	 * Cancel existing job if running and then launch pokemon details including species and
	 * evolution in viewModelScope
	 */
	fun getPokemonDetails(id: Int) {
		loadState.value = true
		getPokemonJob.cancelIfActive()
		getPokemonJob =
				viewModelScope.launch {
					// getting the pokemon details from the id
					PokemonRepository.getPokemonDetails(id).collect {
						isLoading.value = true
						when (it) {
							is ResponseResult.Success -> {
								/* on the successful call , we setup the variables for the next call to get the evolution
								including evolutionId, flavourtext, capture rate and pokemon name
								* */
								isLoading.value = false
								pokemonName = it.data.name!!
								flavourText =
										it.data.flavor_text_entries
												?.filter { it.language.name.equals("en") }
												?.mapIndexed { index, flavorTextEntry ->
													flavorTextEntry.flavor_text.replace("\n", "")
												}!!
												.joinToString("\n")
								evolutionId = it.data.evolution_chain?.url?.extractEvolutionId()!!
								initialCaptureRate = it.data.capture_rate!!
							}
							// signal to show the progress dialog
							is ResponseResult.Progress -> {
								isLoading.value = it.isLoading
							}
							is ResponseResult.Error -> {
								// hide progress dialog and send the error msg
								isLoading.value = false
								loadError.value = it.errmessage
							}
						}
					}
					// collecting the evolution chain flow for the id we have received from the previous API call
					PokemonRepository.getPokemonEvolutionChain(evolutionId).collect {
						isLoading.value = true

						when (it) {
							/* on the successful call , we find the evolution chain and gets the evolution id that could be send to the next call
							to get the specie details of the evolved specie.*/

							is ResponseResult.Success -> {
								isLoading.value = false
								if (
										it.data.chain?.evolvesTo?.isNotEmpty()!! &&
										it.data.chain?.evolvesTo?.get(0)?.evolvesTo!!.isNotEmpty()
								) {
									evolves = true
									evolutionId =
											it.data.chain
													?.evolvesTo
													?.get(0)
													?.evolvesTo
													?.get(0)
													?.species
													?.url
													?.extractId()!!
								} else {
									evolves = false
									pokemonName = "No Evolution exist."
									evolutionId = it.data.chain?.species!!.url!!.extractId()
								}
							}
							is ResponseResult.Progress -> {
								// signal to show the progress dialog

								isLoading.value = it.isLoading
							}
							is ResponseResult.Error -> {
								// hide progress dialog and send the error msg

								isLoading.value = false
								loadError.value = it.errmessage
							}
						}
					}
					// getting the pokemon details from the id

					PokemonRepository.getPokemonDetails(evolutionId).collect {
						isLoading.value = true

						when (it) {
							is ResponseResult.Success -> {
								/* on the successful call , we get the capture rate and setup the mutablestateOf PokemonDetails */
								isLoading.value = false
								if (!evolves) {
									evolveCaptureRate = initialCaptureRate
								} else {
									evolveCaptureRate = it.data.capture_rate!! - initialCaptureRate
								}

								pokemonInfo.value =
										PokemonDetail(
												pokemonName,
												flavourText,
												0,
												it.data.name!!,
												evolutionId.toString().getPicUrl(),
												evolveCaptureRate
										)
							}
							is ResponseResult.Progress -> {
								// signal to show the progress dialog

								isLoading.value = it.isLoading
							}
							is ResponseResult.Error -> {
								// hide progress dialog and send the error msg

								isLoading.value = false
								loadError.value = it.errmessage
							}
						}
					}
				}
	}
}
