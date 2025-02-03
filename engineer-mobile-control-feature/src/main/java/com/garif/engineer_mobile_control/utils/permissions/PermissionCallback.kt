package com.garif.engineer_mobile_control.utils.permissions

interface PermissionCallback {
    fun permissionGranted(permission: PermissionCheckerUtils.RuntimePermissions)
    fun permissionDenied(permission: PermissionCheckerUtils.RuntimePermissions)
}
