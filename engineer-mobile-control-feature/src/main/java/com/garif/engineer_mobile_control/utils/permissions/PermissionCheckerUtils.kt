package com.garif.engineer_mobile_control.utils.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionCheckerUtils {

    private fun isPermissionGranted(
        context: Context,
        permission: RuntimePermissions
    ): Boolean =
        ActivityCompat
            .checkSelfPermission(context, permission.toStringValue()) ==
        PackageManager.PERMISSION_GRANTED

    enum class RuntimePermissions {

        PERMISSION_REQUEST_ACCESS_WIFI {

            override fun toStringValue(): String =
                Manifest.permission.ACCESS_WIFI_STATE

            override fun showInformationMessage(): String = ""
        };

        val value: Int = this.ordinal

        abstract fun toStringValue(): String
        abstract fun showInformationMessage(): String
    }
}
