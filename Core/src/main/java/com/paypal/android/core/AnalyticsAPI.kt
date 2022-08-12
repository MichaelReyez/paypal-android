package com.paypal.android.core

import android.util.Log
import java.net.URL

class AnalyticsAPI {

    private val http = Http()

    suspend fun send(event: FPTIEvent) {
        val url = URL("https://api.sandbox.paypal.com/v1/tracking/batch/events")
        val request = HttpRequest(url, HttpMethod.POST, FPTIUploadRequest(listOf(event)).toJSON().toString())
        val response = http.send(request)
        Log.d("Status", response.status.toString())
    }
}