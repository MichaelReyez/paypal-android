package com.paypal.android.core

import android.util.Log
import java.net.URL

class AnalyticsAPI {

    private val http = Http()

    suspend fun send(event: FPTIEvent) {
        HttpsTrustManager.allowAllSSL();
        val url = URL("https://msmaster.qa.paypal.com:12436/v1/tracking/events/")
        // language=JSON
        val timestamp = System.currentTimeMillis();
        val requestBody = """
            {
              "events": {
                "tracking_event": $timestamp,
                "event_params": {
                  "event_name": "hello_from_team_sdk2"
                }
              }
            }
        """.trimIndent()
        val request = HttpRequest(url, HttpMethod.POST, requestBody)
        val response = http.send(request)
        Log.d("Status", response.status.toString())
    }
}