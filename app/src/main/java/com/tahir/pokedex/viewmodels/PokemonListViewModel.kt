package com.tahir.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tahir.pokedex.constants.AppConstants
import com.tahir.pokedex.pagination.PokemonDataSource
import com.tahir.pokedex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val PokemonRepository: Repository) :
		ViewModel() {
	/**
	 * used as collectAsLazyPagingItems in the UI
	 * @see PokemonList.kt
	 */
	val usersPager = Pager(PagingConfig(pageSize = AppConstants.PAGE_SIZE)) {
		PokemonDataSource(PokemonRepository)
	}.flow.cachedIn(viewModelScope)
}
