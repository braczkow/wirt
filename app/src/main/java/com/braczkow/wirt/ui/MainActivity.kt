package com.braczkow.wirt.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.braczkow.wirt.R
import com.braczkow.wirt.ui.common.BaseFragment
import com.braczkow.wirt.ui.location.LocationFragment
import com.braczkow.wirt.ui.settings.SettingsFragment
import com.ncapdevi.fragnav.FragNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    FragmentNavigation {

    private lateinit var fragNavController: FragNavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragNavController = FragNavController(supportFragmentManager,
            R.id.fragment_container
        )

        val fragments = listOf(
            LocationFragment()
        )

        fragNavController.rootFragments = fragments
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    override fun setSettingsScreen() {
        fragNavController.replaceFragment(SettingsFragment())
    }
}

interface FragmentNavigation {
    fun setSettingsScreen()
}

