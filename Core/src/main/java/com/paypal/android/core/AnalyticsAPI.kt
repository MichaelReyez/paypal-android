package com.paypal.android.core

import android.util.Log
import java.net.URL

class AnalyticsAPI {

    private val http = Http()

    suspend fun send(event: FPTIEvent) {
        val url = URL("https://api.sandbox.paypal.com/v1/tracking/events/")

        // language=JSON
        val timestamp = System.currentTimeMillis();
        val requestBody = """
            {
              "events": {
                "channel": "mobile",
                "tracking_event": $timestamp,
                "event_params": {
                  "e": "la",
                  "t": $timestamp,
                  "event_name": "hello_from_team_sdk",
                }
              }
            }
        """.trimIndent()
        val request = HttpRequest(url, HttpMethod.POST, requestBody)
//        val request = HttpRequest(url, HttpMethod.POST, FPTIUploadRequest(listOf(event)).toJSON().toString())
        val response = http.send(request)
        Log.d("Status", response.status.toString())
    }
}