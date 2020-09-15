package com.braczkow.wirt.ui.common

sealed class PermissionResult(
    val permission: String
) {
    class Granted(p: String): PermissionResult(p)
    class JustDenied(p: String): PermissionResult(p)
    class PermanentlyDenied(p: String): PermissionResult(p)
}