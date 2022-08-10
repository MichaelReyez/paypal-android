package com.paypal.android.core

import org.json.JSONObject

data class FPTIEvent(
    val event: String,
    val timestamp: Long,
    val level: String,
    val payload: Payload
) {

    data class Payload(val from: String, val to: String) {
        fun toJSON() = JSONObject()
            .put("from", from)
            .put("to", to)
    }

    fun toJSON() = JSONObject()
        .put("event", event)
        .put("timeStamp", timestamp)
        .put("level", level)
        .put("payload", payload.toJSON())
}
