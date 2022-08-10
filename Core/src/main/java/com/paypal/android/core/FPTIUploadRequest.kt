package com.paypal.android.core

import org.json.JSONObject

data class FPTIUploadRequest(val tracking: List<FPTITracking>, val events: List<FPTIEvent>) {

    fun toJSON(): JSONObject = JSONObject()
        .put("tracking", tracking.toJSONArray())
        .put("events", events.toJSONArray())
}
