package com.paypal.android.core.analytics

import android.util.Log
import com.paypal.android.core.Http
import com.paypal.android.core.HttpRequestFactory
import java.util.*

internal class AnalyticsService(
    private val deviceInspector: DeviceInspector,
    private val http: Http,
    private val httpRequestFactory: HttpRequestFactory
) {

    internal suspend fun sendAnalyticsEvent(name: String, accessToken: String) {
        val timestamp = System.currentTimeMillis()

        val analyticsEventData =
            AnalyticsEventData(
                name,
                timestamp,
                getSessionId(accessToken),
                deviceInspector.inspect()
            )
        val httpRequest = httpRequestFactory.createHttpRequestForAnalytics(analyticsEventData)

        val response = http.send(httpRequest)
        if (!response.isSuccessful) {
            Log.d("[PayPal SDK]", "Failed to send analytics: ${response.error?.message}")
        }
    }

    companion object {
        private lateinit var currentAccessToken: String
        private fun getSessionId(accessToken: String): String {
            return if (currentAccessToken == accessToken) sessionId
            else {
                currentAccessToken = accessToken
                sessionId = UUID.randomUUID().toString()
                sessionId
            }
        }

        private var sessionId = UUID.randomUUID().toString()
    }
}
