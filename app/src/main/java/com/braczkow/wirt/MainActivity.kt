package com.braczkow.wirt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.braczkow.wirt.openweather.internal.OpenWeatherApi
import com.braczkow.wirt.openweather.WillItRainUseCase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val appId = "ccbab75d0272f13370167dd604890713"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = OpenWeatherApi.Builder(
            appId
        ).build()
        val uc = WillItRainUseCase(api)

        val szczecinDescriptor = WillItRainUseCase.LocationDescriptor.LatLon("53.42894", "14.55302")
        val katowiceDescriptor = WillItRainUseCase.LocationDescriptor.LatLon("50.25841", "19.02754")

        lifecycleScope.launchWhenStarted {
            val willItRain = uc.execute(katowiceDescriptor, WillItRainUseCase.TimeRangeDescriptor.Today)

            main_text.text = "Will it rain: $willItRain"
        }

    }
}

