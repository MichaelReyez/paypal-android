package com.paypal.android.core

import org.json.JSONObject

data class FPTITracking(val eventType: String): JSONRepresentable {

    override fun toJSON(): JSONObject = JSONObject()
        .put("event_type", eventType)
}
