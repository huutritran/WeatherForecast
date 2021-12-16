package com.example.weatherforecast.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.domain.models.WeatherInfo
import com.example.weatherforecast.domain.usecases.NotFound
import com.example.weatherforecast.domain.usecases.SearchMinLength
import com.example.weatherforecast.domain.usecases.SearchWeather
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private lateinit var mockUseCase: SearchWeather
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockUseCase = mockk()
        viewModel = SearchViewModel(mockUseCase)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should not execute usecase when search criteria empty`() {
        //arrange
        viewModel.searchKey.set("")

        //act
        viewModel.search()

        //assert
        coVerify(exactly = 0) { mockUseCase(any()) }
    }

    @Test
    fun `should execute usecase when search criteria not empty`() {
        //arrange
        viewModel.searchKey.set("dummy")

        //act
        viewModel.search()

        //assert
        coVerify(exactly = 1) { mockUseCase(any()) }
    }

    @Test
    fun `should return failure when usecase return failure`() {
        //arrange
        viewModel.searchKey.set("aa")
        coEvery { mockUseCase(any()) } returns Left(SearchMinLength(3))
        val failure = mockk<Observer<Failure>> { every { onChanged(any()) } just Runs }
        viewModel.failure.observeForever(failure)

        //act
        viewModel.search()

        //assert
        verify { failure.onChanged(SearchMinLength(3))  }
        coVerify(exactly = 1) { mockUseCase(any()) }
    }

    @Test
    fun `should return data when usecase return data`() {
        //arrange
        viewModel.searchKey.set("aa")

        val mockData:List<WeatherInfo> = listOf(mockk())
        coEvery { mockUseCase(any()) } returns Right(mockData)

        val data = mockk<Observer<List<WeatherInfo>>> { every { onChanged(any()) } just Runs }
        viewModel.weatherData.observeForever(data)

        //act
        viewModel.search()

        //assert
        verify { data.onChanged(mockData)  }
        coVerify(exactly = 1) { mockUseCase(any()) }
    }

    @Test
    fun `should return loading state correctly when search success`() {
        assertLoadingCorrectly(whenUsecaseReturn = Right(listOf(mockk()))) {
            viewModel.search()
        }
    }

    @Test
    fun `should return loading state correctly when search fail`() {
        assertLoadingCorrectly(whenUsecaseReturn = Left(NotFound)) {
            viewModel.search()
        }
    }

    private fun assertLoadingCorrectly(
        whenUsecaseReturn:  Either<Failure, List<WeatherInfo>>,
        act: () -> Unit) {
        val loadingObserver = mockk<Observer<Boolean>> { every { onChanged(any()) } just Runs }
        viewModel.loading.observeForever(loadingObserver)
        viewModel.searchKey.set("dummySearch")
        coEvery { mockUseCase(any()) } returns whenUsecaseReturn

        //act
        act()

        //assert
        verifySequence {
            loadingObserver.onChanged(true)
            loadingObserver.onChanged(false)
        }
        coVerify { mockUseCase(SearchWeather.Params("dummySearch".lowercase())) }
    }
}