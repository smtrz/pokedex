package com.tahir.pokedex.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tahir.pokedex.fakeRepository.FakeRemoteRepoImpl
import com.tahir.pokedex.generics.ResponseResult
import com.tahir.pokedex.helpers.MainCoroutineRule
import com.tahir.pokedex.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.fail
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PokemonDetailViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository
    lateinit var viewModel: PokemonDetailViewModel

    lateinit var fakeRepoImpl: FakeRemoteRepoImpl

    @Before
    fun setUp() {
        fakeRepoImpl = FakeRemoteRepoImpl()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Pokemon details are returned`() = runBlocking {
        try {

            `when`(repository.getPokemonEvolutionChain(2))
                .thenReturn(
                    flow {
                        emit(ResponseResult.Success(fakeRepoImpl.getEvolutionChain(5).body()!!))
                    }
                )
            `when`(repository.getPokemonDetails(2))
                .thenReturn(
                    flow {
                        emit(ResponseResult.Success(fakeRepoImpl.getSpeciesDetails(5).body()!!))
                    }
                )
            viewModel = PokemonDetailViewModel(repository)
            viewModel.getPokemonDetails(2)
            Assert.assertTrue(viewModel.pokemonInfo.value.evolvePokemonName.equals("charmeleon"))

        } catch (exception: Exception) {
            println(exception.message)
            fail()
        }
    }

    @After
    fun tearDown() {
    }
}
