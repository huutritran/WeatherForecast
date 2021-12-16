package com.example.weatherforecast.data.repositories

import arrow.core.Either.Right
import com.example.weatherforecast.constant.Config
import com.example.weatherforecast.data.sources.local.WeatherDAO
import com.example.weatherforecast.data.sources.local.entities.WeatherEntity
import com.example.weatherforecast.data.sources.remote.WeatherApi
import com.example.weatherforecast.data.sources.remote.dto.CityDto
import com.example.weatherforecast.data.sources.remote.dto.DailyWeatherResponse
import com.example.weatherforecast.data.sources.remote.dto.WeatherDto
import com.example.weatherforecast.domain.repositories.WeatherRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest {
    private lateinit var repository: WeatherRepository
    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var mockWeatherApi: WeatherApi
    private lateinit var mockWeatherDAO: WeatherDAO

    @Before
    fun setup() {
        mockWeatherApi = mockk()
        mockWeatherDAO = mockk()
        testDispatcher = TestCoroutineDispatcher()
        repository = WeatherRepositoryImpl(
            ioDispatcher = testDispatcher,
            weatherApi = mockWeatherApi,
            weatherDAO = mockWeatherDAO
        )
    }

    @Test
    fun `should receive data from cache when data valid`() = runBlockingTest {
        //arrange
        val mockCache = listOf(dummyEntity)
        coEvery { mockWeatherDAO.getAllBySearchKey(any()) } returns mockCache

        //act
        val result = repository.getDailyForecast("saigon")

        //assert
        result shouldBe Right(listOf(dummyEntity.toWeatherInfo()))
        coVerify { mockWeatherDAO.getAllBySearchKey("saigon") }
    }

    @Test
    fun `should receive data from api when cache invalid`() = runBlockingTest {
        //arrange
        coEvery { mockWeatherDAO.getAllBySearchKey(any()) } returns emptyList()
        coEvery { mockWeatherApi.getDailyWeather(any()) } returns dummyResponse
        every { mockWeatherDAO.insertALl(any()) } returns Unit

        //act
        val result = repository.getDailyForecast("saigon")

        //assert
        result shouldBe Right(dummyResponse.toWeatherInfoList())
        coVerify { mockWeatherDAO.getAllBySearchKey("saigon") }
        coVerify { mockWeatherApi.getDailyWeather(any()) }
        verify { mockWeatherDAO.insertALl(any()) }
    }

    @Test
    fun `should delete cache when out of date`() = runBlockingTest {
        //arrange
        val outDateEntity = dummyEntity.copy(
            createdDate = getDateBefore(Config.Cache.MAX_CACHE_DAYS + 1)
        )
        val mockCache = listOf(outDateEntity)
        coEvery { mockWeatherDAO.getAllBySearchKey(any()) } returns mockCache
        coEvery { mockWeatherDAO.deleteAllBySearchKey(any()) } returns Unit

        //act
        repository.getDailyForecast("saigon")

        //
        coVerify { mockWeatherDAO.deleteAllBySearchKey("saigon") }
        coVerify { mockWeatherDAO.getAllBySearchKey("saigon") }
    }

    private fun getDateBefore(numberOfDate: Int): Date {
        val today = Date()
        val dateBeforeInMilliseconds = today.time - (numberOfDate * 24 * 60 * 60 * 1000)
        return Date(dateBeforeInMilliseconds)
    }

    private companion object {
        val dummyEntity = WeatherEntity(
            id = 0,
            searchKey = "",
            city = "saigon",
            date = Date(),
            averageTemperature = 0.0,
            createdDate = Date(),
            description = "description",
            humidity = 10,
            pressure = 0.0
        )

        val dummyWeatherDto = WeatherDto(dt = 1)

        val dummyResponse = DailyWeatherResponse(
            cityDto = CityDto(),
            cod = null,
            message = null,
            cnt = null,
            weatherList = listOf(dummyWeatherDto)
        )
    }
}