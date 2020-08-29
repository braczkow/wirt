package com.braczkow.wirt.ui.common

import android.content.Context
import androidx.fragment.app.Fragment
import com.braczkow.wirt.ui.FragmentNavigation

abstract class BaseFragment : Fragment() {

    private var fragmentNavigation: FragmentNavigation? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigation = (context as FragmentNavigation)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentNavigation = null
    }

    fun requireFragmentNavigation(): FragmentNavigation {
        return fragmentNavigation ?: throw IllegalStateException("Requiring navigation in detached state")
    }
}