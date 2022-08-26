package com.tahir.pokedex.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tahir.pokedex.constants.AppConstants.Companion.PAGE_SIZE
import com.tahir.pokedex.constants.AppConstants.Companion.STARTING_OFFSET_INDEX
import com.tahir.pokedex.models.species.Results
import com.tahir.pokedex.repository.Repository
import okio.IOException
import retrofit2.HttpException

/**
 * class that inherits from PagingSource, definition for load and getRefreshkey is overridden
 * @param repo
 */
class PokemonDataSource(private val repo: Repository) : PagingSource<Int, Results>() {

	override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
		return state.anchorPosition
	}

	/**
	 * setting up the offset and prev key,next key, and loads the page data from the repository
	 * @param params
	 */
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
		val offset = params.key ?: STARTING_OFFSET_INDEX

		return try {
			val specieList = repo.getPaginatedPokemons(offset = offset, limit = PAGE_SIZE)
			LoadResult.Page(
					data = specieList.results,
					prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - PAGE_SIZE,
					nextKey = if (specieList.next == null) null else offset + PAGE_SIZE
			)
		} catch (exception: IOException) {
			return LoadResult.Error(exception)
		} catch (exception: HttpException) {
			return LoadResult.Error(exception)
		}
	}

}