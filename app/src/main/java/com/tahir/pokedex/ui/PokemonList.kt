package com.tahir.pokedex.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tahir.pokedex.navigation.Screen
import com.tahir.pokedex.models.Pokemon
import com.tahir.pokedex.utils.extractId
import com.tahir.pokedex.utils.getPicUrl
import com.tahir.pokedex.viewmodels.PokemonListViewModel

/**
 * PokeMonCardItem design
 * @param pokemon, navController.
 */
@Composable
fun PokemonCardItem(
		pokemon: Pokemon,
		navController: NavController
) {

	Card(
            Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                                Screen.PokemonDetailscreen.withArgs(
                                        pokemon.id.toString()
                                )
                        )

                    },
			shape = RoundedCornerShape(10.dp),
			elevation = 5.dp


	) {

		Row(verticalAlignment = Alignment.CenterVertically) {
			ImageLoader(pokemon.imageUrl!!)
			Spacer(modifier = Modifier.width(8.dp))
			Text(
					text = pokemon.name!!,
					style = MaterialTheme.typography.h6,
					modifier = Modifier.padding(8.dp)
			)
		}
	}
}

/**
 * PokeMonList design
 * @param navController
 */
@Composable
fun PokeMonList(
		navController: NavController,
		pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {
	val pokemonPagingList = pokemonListViewModel.usersPager.collectAsLazyPagingItems()

	LazyColumn {

		items(pokemonPagingList) { pokemon ->
			pokemon?.let {
				val id = pokemon.url!!.extractId()
				var poke = Pokemon(
						pokemon.name,
						id,
						pokemon.url!!.getPicUrl()
				)
				PokemonCardItem(poke, navController)
			}


		}

	}
	Box(
			contentAlignment = Center,
			modifier = Modifier.fillMaxSize()
	) {
		pokemonPagingList.apply {
			when {
				loadState.refresh is LoadState.Loading -> {
					CircularProgressIndicator(color = MaterialTheme.colors.primary)

				}
				loadState.append is LoadState.Loading -> {
					CircularProgressIndicator(color = MaterialTheme.colors.primary)

				}
				loadState.refresh is LoadState.Error -> {
					val e = pokemonPagingList.loadState.refresh as LoadState.Error
					RetrySection(error = e.error.message!!) {
						pokemonPagingList.retry()
					}
				}
			}

		}


	}
}

/**
 * RetrySection design
 * @param error
 */
@Composable
fun RetrySection(
		error: String,
		onRetry: () -> Unit
) {
	Column {
		Text(error, color = androidx.compose.ui.graphics.Color.Red, fontSize = 18.sp)
		Spacer(modifier = Modifier.height(8.dp))
		Button(
				onClick = { onRetry() },
				modifier = Modifier.align(CenterHorizontally)
		) {
			Text(text = "Retry")
		}
	}
}
