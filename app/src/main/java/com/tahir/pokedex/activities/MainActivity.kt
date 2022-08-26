package com.tahir.pokedex.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tahir.pokedex.navigation.Navigation
import com.tahir.pokedex.ui.pokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			pokedexTheme {
				Navigation()

			}

		}


	}


}