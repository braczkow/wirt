package com.braczkow.wirt.ui.common

import android.content.Context
import android.content.pm.PackageManager
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

    fun requestPermissions(permissions: List<String>) {
        requestPermissions(permissions.toTypedArray(), 1)
    }

    open fun onPermissionsResult(permissionResults: List<PermissionResult>) {
    }

    final override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        val permissionResults = permissions.mapIndexed { index, permission ->
            when(grantResults[index]) {
                PackageManager.PERMISSION_GRANTED -> PermissionResult.Granted(permission)
                PackageManager.PERMISSION_DENIED -> {
                    if (shouldShowRequestPermissionRationale(permission)) {
                        PermissionResult.JustDenied(permission)
                    } else {
                        PermissionResult.PermanentlyDenied(permission)
                    }
                }
                else -> throw IllegalArgumentException("Unknown grantResult")
            }
        }


        onPermissionsResult(permissionResults)
    }
}