package com.braczkow.openweatherr


import com.braczkow.openweatherr.internal.OpenWeatherApi
import com.braczkow.openweatherr.testdata.OpenWeatherOneCallResponses
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class WillItRainUseCaseTest {
    lateinit var UT: WillItRainUseCase

    //mocks
    val openWeatherApi: OpenWeatherApi = mockk()

    @Before
    fun setup() {
        com.braczkow.lib.Timee.fix(OpenWeatherOneCallResponses.NOW)
        UT = WillItRainUseCase(openWeatherApi)
    }

    @After
    fun tearDown() {
        com.braczkow.lib.Timee.unfix()
    }

    @Test
    fun today_noRainAtAll_NoRainReturned() = runBlockingTest {
        //arrange
        coEvery {
            openWeatherApi.getOneCallByLatLon(any(), any(), any())
        } returns OpenWeatherOneCallResponses.withoutCurrentRainNullHourly

        //act
        val result = UT.execute(
            WillItRainUseCase.LocationDescriptor.LatLon("12", "23"),
            WillItRainUseCase.TimeRangeDescriptor.Today
        )

        assertThat(result).isEqualTo(WillItRainUseCase.Result.NoRain)
    }

    @Test
    fun today_currentRain_IsRainingReturned() = runBlockingTest {
        //arrange
        coEvery {
            openWeatherApi.getOneCallByLatLon(any(), any(), any())
        } returns OpenWeatherOneCallResponses.currentRainNullHourly

        //act
        val result = UT.execute(
            WillItRainUseCase.LocationDescriptor.LatLon("12", "23"),
            WillItRainUseCase.TimeRangeDescriptor.Today
        )

        assertThat(result).isEqualTo(WillItRainUseCase.Result.IsRaining)
    }

    @Test
    fun today_RainInAnHour_WilRainReturned() = runBlockingTest {
        //arrange
        coEvery {
            openWeatherApi.getOneCallByLatLon(any(), any(), any())
        } returns OpenWeatherOneCallResponses.rainInAnHour

        //act
        val result = UT.execute(
            WillItRainUseCase.LocationDescriptor.LatLon("12", "23"),
            WillItRainUseCase.TimeRangeDescriptor.Today
        )

        assertThat(result).isEqualTo(WillItRainUseCase.Result.WillRain)
    }

    @Test
    fun today_RainIn40Hours_NoRainReturned() = runBlockingTest {
        //arrange
        coEvery {
            openWeatherApi.getOneCallByLatLon(any(), any(), any())
        } returns OpenWeatherOneCallResponses.rainIn40Hours

        //act
        val result = UT.execute(
            WillItRainUseCase.LocationDescriptor.LatLon("12", "23"),
            WillItRainUseCase.TimeRangeDescriptor.Today
        )

        assertThat(result).isEqualTo(WillItRainUseCase.Result.NoRain)
    }

    @Test
    fun hours_RainIn40Hours_NoRainReturned() = runBlockingTest {
        //arrange
        coEvery {
            openWeatherApi.getOneCallByLatLon(any(), any(), any())
        } returns OpenWeatherOneCallResponses.rainIn40Hours

        //act
        val result = UT.execute(
            WillItRainUseCase.LocationDescriptor.LatLon("12", "23"),
            WillItRainUseCase.TimeRangeDescriptor.Hours(4)
        )

        assertThat(result).isEqualTo(WillItRainUseCase.Result.NoRain)
    }

    @Test
    fun hours_RainInAnHours_WillRainReturned() = runBlockingTest {
        //arrange
        coEvery {
            openWeatherApi.getOneCallByLatLon(any(), any(), any())
        } returns OpenWeatherOneCallResponses.rainInAnHour

        //act
        val result = UT.execute(
            WillItRainUseCase.LocationDescriptor.LatLon("12", "23"),
            WillItRainUseCase.TimeRangeDescriptor.Hours(4)
        )

        assertThat(result).isEqualTo(WillItRainUseCase.Result.WillRain)
    }

    @Test
    fun hours_currentRain_IsRainingReturned() = runBlockingTest {
        //arrange
        coEvery {
            openWeatherApi.getOneCallByLatLon(any(), any(), any())
        } returns OpenWeatherOneCallResponses.currentRainNullHourly

        //act
        val result = UT.execute(
            WillItRainUseCase.LocationDescriptor.LatLon("12", "23"),
            WillItRainUseCase.TimeRangeDescriptor.Hours(4)
        )

        assertThat(result).isEqualTo(WillItRainUseCase.Result.IsRaining)
    }

}