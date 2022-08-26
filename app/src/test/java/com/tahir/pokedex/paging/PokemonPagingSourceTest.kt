package com.tahir.pokedex.paging

import androidx.paging.PagingSource
import com.tahir.pokedex.constants.AppConstants
import com.tahir.pokedex.fakeRepository.FakeRemoteRepoImpl
import com.tahir.pokedex.helpers.MainCoroutineRule
import com.tahir.pokedex.pagination.PokemonDataSource
import com.tahir.pokedex.repository.RemoteDataSource
import com.tahir.pokedex.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PokemonPagingSourceTest {
    @get:Rule var mainCoroutineRule = MainCoroutineRule()
    lateinit var repository: Repository
    lateinit var fakeRepoImpl: FakeRemoteRepoImpl
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        fakeRepoImpl = FakeRemoteRepoImpl()
        remoteDataSource = RemoteDataSource(fakeRepoImpl)
        repository = Repository(remoteDataSource)
    }

    @Test
    fun successfulLoadOfItems() = runTest {
        val pagingSource = PokemonDataSource(repository)

        val expected =
            PagingSource.LoadResult.Page(
                data = repository.getPaginatedPokemons(0, AppConstants.PAGE_SIZE).results,
                prevKey = null,
                nextKey = 100
            )
        val actual =
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = AppConstants.PAGE_SIZE,
                    placeholdersEnabled = false
                )
            )
        assertEquals(expected, actual)
    }
}
