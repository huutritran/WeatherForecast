package com.example.weatherforecast.domain.usecases

import arrow.core.Either.Right
import arrow.core.Either.Left
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.domain.entities.WeatherInfo
import com.example.weatherforecast.domain.repositories.WeatherRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchWeatherTest {
    lateinit var mockWeatherRepository: WeatherRepository
    lateinit var searchWeather: SearchWeather

    @Before
    fun setup() {
        mockWeatherRepository = mockk()
        searchWeather = SearchWeather(mockWeatherRepository)
    }

    @Test
    fun `should get data from WeatherRepository`() = runBlockingTest {
        //arrange
        val mockWeather: WeatherInfo = mockk()
        coEvery { mockWeatherRepository.getDailyForecast(any()) } returns Right(listOf(mockWeather))

        //act
        val result = searchWeather(SearchWeather.Params("saigon"))

        //assert
        result shouldBe Right(listOf(mockWeather))
        coEvery { mockWeatherRepository.getDailyForecast("saigon") }
    }

    @Test
    fun `should return failure when WeatherRepository failure`() = runBlockingTest {
        //arrange
        coEvery { mockWeatherRepository.getDailyForecast(any()) } returns Left(Failure.ServerError)

        //act
        val result = searchWeather(SearchWeather.Params("saigon"))

        //assert
        result shouldBe Left(Failure.ServerError)
        coEvery { mockWeatherRepository.getDailyForecast("saigon") }
    }

    @Test
    fun `should return SearchMinLength failure when city name length less than 3`() = runBlockingTest {
        //act
        val result = searchWeather(SearchWeather.Params("sa"))

        //assert
        result shouldBe Left(SearchWeatherFailure.SearchMinLength(3))
    }
}