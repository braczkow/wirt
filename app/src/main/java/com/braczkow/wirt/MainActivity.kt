package com.braczkow.wirt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.braczkow.wirt.openweather.internal.OpenWeatherApi
import com.braczkow.wirt.openweather.WillItRainUseCase
import com.ncapdevi.fragnav.FragNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragNavController = FragNavController(supportFragmentManager, R.id.fragment_container)

        val fragments = listOf(
            LocationFragment()
        )

        fragNavController.rootFragments = fragments
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)


//        val api = OpenWeatherApi.Builder(
//            appId
//        ).build()
//        val uc = WillItRainUseCase(api)
//
//        val szczecinDescriptor = WillItRainUseCase.LocationDescriptor.LatLon("53.42894", "14.55302")
//        val katowiceDescriptor = WillItRainUseCase.LocationDescriptor.LatLon("50.25841", "19.02754")
//
//        lifecycleScope.launchWhenStarted {
//            val willItRain = uc.execute(katowiceDescriptor, WillItRainUseCase.TimeRangeDescriptor.Today)
//        }

    }
}

