package com.paypal.android.core

import org.json.JSONArray

fun List<JSONRepresentable>.toJSONArray(): JSONArray {
    val result = JSONArray()
    for (item in this) {
        result.put(item.toJSON())
    }
    return result
}
