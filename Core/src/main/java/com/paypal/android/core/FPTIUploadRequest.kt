package com.paypal.android.core

import org.json.JSONObject

data class FPTIUploadRequest(val events: List<FPTIEvent>) {

    fun toJSON(): JSONObject = JSONObject()
        .put("events", events.toJSONArray())
}
