package com.nextsolutions.sprintsphere_pro.domain.utils.Location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<Location>

    class PermissionNotGrantedException(message: String): Exception()
    class GpsDisabledException(message: String): Exception()
}