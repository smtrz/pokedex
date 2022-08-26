package com.tahir.pokedex.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.tahir.pokedex.R
import com.tahir.pokedex.models.PokemonDetail
import com.tahir.pokedex.viewmodels.PokemonDetailViewModel

/**
 * Pokemon profile screen,
 * @param id , navController
 */
@Composable
fun ProfileScreen(
	id: Int,
	navController: NavController,
	pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel()
) {
	// mutableStateOf variables that are observed by the composable.

	val pokemonsDetails by remember { pokemonDetailViewModel.pokemonInfo }
	val loading by remember { pokemonDetailViewModel.isLoading }
	val loadState by pokemonDetailViewModel.loadState.observeAsState()
// getting the information from the view model using launchedeffect to avoid most of the side effects by recomposition.
	if (!loadState!!) {
		LaunchedEffect(key1 = true) {
			pokemonDetailViewModel.getPokemonDetails(id)
		}
	}


	Column(modifier = Modifier.fillMaxSize()) {
		// if the loading is true show progress dialog.
		if (loading) {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {
				CircularProgressIndicator(color = MaterialTheme.colors.primary)
			}
		}
		BoxWithConstraints(modifier = Modifier.weight(1f)) {
			Surface {
				Column(
					modifier = Modifier
						.fillMaxSize()
				) {
					ProfileHeader(
						pokemonsDetails,
						this@BoxWithConstraints.maxHeight
					)
					ProfileContent(pokemonsDetails, this@BoxWithConstraints.maxHeight)
				}
			}

		}
	}
}

@Composable
private fun ProfileHeader(
	pokemonDetail: PokemonDetail,
	containerHeight: Dp
) {

	Image(
		modifier = Modifier
			.heightIn(max = containerHeight / 2)
			.fillMaxWidth(),
		painter = rememberAsyncImagePainter(pokemonDetail.evolveePokemonImageUrl),
		contentScale = ContentScale.Crop,
		contentDescription = null
	)
}

@Composable
private fun ProfileContent(pokemonDetail: PokemonDetail, containerHeight: Dp) {
	Column {
		Spacer(modifier = Modifier.height(8.dp))
		Name(pokemonDetail)
		pokemonDetail.name?.let { ProfileProperty(stringResource(R.string.evolvedfrom), it) }
		ProfileProperty(
			stringResource(R.string.CaptureRate),
			pokemonDetail.captureDifference.toString()
		)
		ProfileProperty(stringResource(R.string.flavouredText), pokemonDetail.flavourText!!)

		Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
	}
}

@Composable
private fun Name(
	pokemonDetail: PokemonDetail
) {
	Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
		Name(
			pokemonDetail = pokemonDetail,
			modifier = Modifier.height(32.dp)
		)
	}
}

@Composable
private fun Name(pokemonDetail: PokemonDetail, modifier: Modifier = Modifier) {
	Text(
		text = pokemonDetail.evolvePokemonName!!,
		modifier = modifier,
		style = MaterialTheme.typography.h5,
		fontWeight = FontWeight.Bold
	)
}

@Composable
fun ProfileProperty(label: String, value: String) {
	val scroll = rememberScrollState(0)

	Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
		Divider()
		CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
			Text(
				text = label,
				modifier = Modifier.height(24.dp),
				style = MaterialTheme.typography.caption,
			)
		}
		val style = if (label.equals(stringResource(id = R.string.CaptureRate))) {
			if (value.toInt() < 0) {
				MaterialTheme.typography.body1.copy(color = Color.Red)

			} else {
				MaterialTheme.typography.body1.copy(color = Color.Green)

			}
		} else {
			MaterialTheme.typography.body1
		}
		Text(
			text = value,
			modifier = Modifier
				.widthIn(32.dp)
				.verticalScroll(scroll),

			style = style
		)
	}

}



