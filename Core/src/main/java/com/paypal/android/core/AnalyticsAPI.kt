package com.paypal.android.core

import java.net.URL

class AnalyticsAPI {

    private val http = Http()

    fun send(event: FPTIEvent) {
        val request = HttpRequest(URL("https://fpti.biz"), HttpMethod.GET)


    }
}