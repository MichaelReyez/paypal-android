package com.paypal.android.core

import org.json.JSONObject

interface JSONRepresentable {
    fun toJSON(): JSONObject
}